package org.dante.springboot.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springboot.security.config.EasyUITreeConstant;
import org.dante.springboot.security.dao.ResourceDao;
import org.dante.springboot.security.dao.ResourceSpecs;
import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.domain.Resource;
import org.dante.springboot.security.dto.req.ResourceReqDto;
import org.dante.springboot.security.dto.resp.ResourceRespDto;
import org.dante.springboot.security.dto.resp.ResourceTreeAttribute;
import org.dante.springboot.security.dto.resp.ResourceTreeDto;
import org.dante.springboot.security.pub.EasyUIDragTreeReq;
import org.dante.springboot.security.pub.LoginUser;
import org.dante.springboot.security.util.LoginUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
@Transactional(readOnly = true)
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	public List<Resource> findAll() {
		return resourceDao.findAll();
	}

	/**
	 * 获取菜单树
	 * 
	 * @return
	 */
	public List<ResourceTreeDto> findResourceTree() {
		List<ResourceTreeDto> trees = Lists.newArrayList();
		List<Resource> roots = resourceDao.findRootResource();
		if (!CollectionUtils.isEmpty(roots)) {
			for (Resource resource : roots) {
				ResourceTreeDto tree = convertToResourceTree(resource);
				trees.add(tree);
				buildResourceTree(tree);
			}
		}
		return trees;
	}
	
	/**
	 * 获取登录用户的可见菜单
	 * 
	 * @return
	 */
	public List<ResourceTreeDto> findUserResourceTree() {
		List<ResourceTreeDto> userMenus = Lists.newArrayList(); 
		LoginUser loginUser = LoginUserUtil.loginUser();
		
		if(loginUser == null) {
			return Lists.newArrayList();
		}
		List<Long> pids = resourceDao.findAllParentId();
		List<Resource> resources = resourceDao.findAll(ResourceSpecs.findResourceTreeByUserId(loginUser.getId()));
		Map<Long, ResourceTreeDto> menuTreeMap = Maps.newLinkedHashMap();
		for (Resource resource : resources) {
			menuTreeMap.put(resource.getId(), convertToResourceTree(resource));
		}
		List<ResourceTreeDto> trees = buildUserResourceTree(pids, menuTreeMap);
		for (ResourceTreeDto tree : trees) {
			if(!tree.getChildren().isEmpty() || !pids.contains(tree.getAttributes().getParentId())) {
				userMenus.add(tree);
			}
		}
		return userMenus;
	}
	
	private List<ResourceTreeDto> buildUserResourceTree(List<Long> pids, Map<Long, ResourceTreeDto> menuTreeMap) {
		List<ResourceTreeDto> menus = Lists.newArrayList();
		Set<Long> keySet = menuTreeMap.keySet();
		for (Long key : keySet) {
			ResourceTreeDto menu = menuTreeMap.get(key);
			Long parentId = menu.getAttributes().getParentId();
			if(parentId == null) {
				menus.add(menu);
			} else {
				ResourceTreeDto m = menuTreeMap.get(parentId);
				if(m != null) {
					m.getChildren().add(menu);
				}
			}
		}
		
		
		if(!CollectionUtils.isEmpty(pids)) {
			for (Long pid : pids) {
				removeEmptyMenu(pid, menuTreeMap);
			}
		}
		return menus;
	}
	
	private void removeEmptyMenu(Long pid, Map<Long, ResourceTreeDto> menuTreeMap) {
		ResourceTreeDto menu = menuTreeMap.get(pid);
		if(menu == null || !CollectionUtils.isEmpty(menu.getChildren())) {
			return;
		}
		Long parentId = menu.getAttributes().getParentId();
		ResourceTreeDto subMenu = menuTreeMap.get(parentId);
		if(subMenu != null) {
			subMenu.getChildren().remove(menu);
			removeEmptyMenu(subMenu.getId(), menuTreeMap);
		}
	}

	@Transactional
	public ResourceRespDto updateResource(ResourceReqDto resourceReq) {
		Resource resource = new Resource();
		BeanUtils.copyProperties(resourceReq, resource);
		resource.setAuthority(new Authority(resourceReq.getAuthorityId()));
		Long pid = resourceReq.getPid();
		if (pid != null && pid > 0) {
			resource.setParentResource(new Resource(pid));
		}
		resourceDao.save(resource);
		StringBuilder fullIdBuilder = new StringBuilder(resource.getId() + "");
		buildFullId(pid, fullIdBuilder);
		resource.setFullId(fullIdBuilder.toString());
		resourceDao.save(resource);
		ResourceRespDto resourceResp = new ResourceRespDto();
		BeanUtils.copyProperties(resourceReq, resourceResp);
		resourceResp.setId(resource.getId());
		return resourceResp;
	}

	@Transactional
	public void deleteById(Long id) {
		List<Resource> resources = resourceDao.findByPid(id);
		if (!CollectionUtils.isEmpty(resources)) {
			resourceDao.deleteInBatch(resources);
		}
		resourceDao.delete(id);
	}

	private void buildResourceTree(ResourceTreeDto tree) {
		Long id = tree.getId();
		List<Resource> childResources = resourceDao.findByPid(id);
		if (!CollectionUtils.isEmpty(childResources)) {
			for (Resource childResource : childResources) {
				ResourceTreeDto childTree = convertToResourceTree(childResource);
				tree.getChildren().add(childTree);
				buildResourceTree(childTree);
			}
		}
	}

	private ResourceTreeDto convertToResourceTree(Resource resource) {
		ResourceTreeDto tree = new ResourceTreeDto();
		tree.setId(resource.getId());
		tree.setText(resource.getName());

		ResourceTreeAttribute attributes = new ResourceTreeAttribute();
		Resource parentResource = resource.getParentResource();
		if (parentResource != null) {
			attributes.setParentId(parentResource.getId());
			attributes.setParentName(parentResource.getName());
		}
		attributes.setId(resource.getId());
		attributes.setName(resource.getName());
		attributes.setUrl(resource.getUrl());
		attributes.setType(resource.getType());
		attributes.setShowOrder(resource.getShowOrder());
		Authority authority = resource.getAuthority();
		if (authority != null) {
			attributes.setAuthorityId(authority.getId());
		}
		tree.setAttributes(attributes);
		return tree;
	}

	private void buildFullId(Long pid, StringBuilder fullIdBuilder) {
		if (pid == null || pid != null && pid < 0) {
			return;
		}
		fullIdBuilder.append("-").append(pid);
		Resource parentResource = resourceDao.findOne(pid);
		if (parentResource.getParentResource() != null) {
			buildFullId(parentResource.getParentResource().getId(), fullIdBuilder);
		}
	}

	@Transactional
	public void updateAuthorityWhenDrag(EasyUIDragTreeReq dragTreeReq) throws Exception {
		String point = dragTreeReq.getPoint();
		if (EasyUITreeConstant.POINT_APPEND.equalsIgnoreCase(point)) {
			handleDragAppend(dragTreeReq.getTargetId(), dragTreeReq.getSourceId());
		} else if (EasyUITreeConstant.POINT_TOP.equalsIgnoreCase(point)) {
			handleDragTop(dragTreeReq.getTargetPid(), dragTreeReq.getTargetShowOrder(), dragTreeReq.getSourceId());
		} else if (EasyUITreeConstant.POINT_BOTTOM.equalsIgnoreCase(point)) {
			handleDragBottom(dragTreeReq.getTargetPid(), dragTreeReq.getTargetShowOrder(), dragTreeReq.getSourceId());
		} else {
			throw new Exception("Drag point mush match 'append' 'top' 'bottom'");
		}
	}

	/**
	 * 将源节点移动到目标节点的上方 showOrder(source) = showOrder(target) - 1, pid(source) =
	 * pid(target)
	 * 
	 * @param targetPid
	 * @param targetShowOrder
	 * @param sourceId
	 */
	private void handleDragTop(Long targetPid, int targetShowOrder, Long sourceId) {
		Resource sourceResource = resourceDao.findOne(sourceId);
		sourceResource.setParentResource(new Resource(targetPid));
		sourceResource.setShowOrder(targetShowOrder > 1 ? targetShowOrder - 1 : 1);
		resourceDao.save(sourceResource);
	}

	/**
	 * 将源节点移动到目标节点的下方 showOrder(source) = showOrder(target) - 1, pid(source) =
	 * pid(target)
	 * 
	 * @param targetPid
	 * @param targetShowOrder
	 * @param sourceId
	 */
	private void handleDragBottom(Long targetPid, int targetShowOrder, Long sourceId) {
		Resource sourceResource = resourceDao.findOne(sourceId);
		sourceResource.setParentResource(new Resource(targetPid));
		sourceResource.setShowOrder(targetShowOrder + 1);
		resourceDao.save(sourceResource);
	}

	/**
	 * 将源节点移动到目标节点内 pid(source) = id(target)
	 * 
	 * @param targetId
	 * @param sourceId
	 */
	private void handleDragAppend(Long targetId, Long sourceId) {
		Resource sourceResource = resourceDao.findOne(sourceId);
		Resource parentResource = null;
		if (targetId > 0) {
			parentResource = new Resource(targetId);
		}
		sourceResource.setParentResource(parentResource);
		resourceDao.save(sourceResource);
	}

}
