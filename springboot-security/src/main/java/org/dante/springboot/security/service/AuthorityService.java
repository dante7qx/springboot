package org.dante.springboot.security.service;

import java.util.List;

import org.dante.springboot.security.config.EasyUITreeConstant;
import org.dante.springboot.security.dao.AuthorityDao;
import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.dto.req.AuthorityReqDto;
import org.dante.springboot.security.dto.resp.AuthorityRespDto;
import org.dante.springboot.security.dto.resp.AuthorityTreeDto;
import org.dante.springboot.security.pub.EasyUIDragTreeReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;
	
	public List<AuthorityTreeDto> findAuthorityTree() throws Exception {
		List<AuthorityTreeDto> trees = Lists.newArrayList();
		List<Authority> authoritys = authorityDao.findRootAuthority();
		if(!CollectionUtils.isEmpty(authoritys)) {
			for (Authority authority : authoritys) {
				AuthorityTreeDto tree = convertAuthorityToTree(authority);
				trees.add(tree);
				buildAuthorityTree(tree);
			}
		}
		return trees;
	}
	
	private void buildAuthorityTree(AuthorityTreeDto tree) throws Exception {
		Long id = tree.getId();
		List<Authority> childAuthoritys = authorityDao.findByParentId(id);
		if(!CollectionUtils.isEmpty(childAuthoritys)) {
			for (Authority authority : childAuthoritys) {
				AuthorityTreeDto childTree = convertAuthorityToTree(authority);
				tree.addChildren(childTree);
				buildAuthorityTree(childTree);
			}
		} else {
			tree.setState("open");
		}
	}
	
	private AuthorityTreeDto convertAuthorityToTree(Authority authority) {
		AuthorityTreeDto tree = new AuthorityTreeDto();
		tree.setId(authority.getId());
		tree.setText(authority.getName());
		tree.setAttributes(authority);
		return tree;
	}
	
	public List<Authority> findByPid(Long pid) throws Exception {
		return authorityDao.findByParentId(pid);
	}
	
	@Transactional
	public AuthorityRespDto updateAuthority(AuthorityReqDto authorityReq) {
		Authority authority = new Authority();
		BeanUtils.copyProperties(authorityReq, authority);
		if(authorityReq.getPid() != null) {
			authority.setParentAuthority(new Authority(authorityReq.getPid()));
		}
		authority = authorityDao.save(authority);
		AuthorityRespDto authorityResp = new AuthorityRespDto();
		BeanUtils.copyProperties(authorityReq, authorityResp);
		authorityResp.setId(authority.getId());
		return authorityResp;
	}
	
	@Transactional
	public void deleteById(Long id) throws Exception {
		List<Authority> authoritys = authorityDao.findByParentId(id);
		if(!CollectionUtils.isEmpty(authoritys)) {
			authorityDao.deleteInBatch(authoritys);
		}
		authorityDao.delete(id);
	}
	
	@Transactional
	public void updateAuthorityWhenDrag(EasyUIDragTreeReq dragTreeReq) throws Exception {
		String point = dragTreeReq.getPoint();
		if(EasyUITreeConstant.POINT_APPEND.equalsIgnoreCase(point)) {
			handleDragAppend(dragTreeReq.getTargetId(), dragTreeReq.getSourceId());
		} else if(EasyUITreeConstant.POINT_TOP.equalsIgnoreCase(point)) {
			handleDragTop(dragTreeReq.getTargetPid(), dragTreeReq.getTargetShowOrder(), dragTreeReq.getSourceId());
		} else if(EasyUITreeConstant.POINT_BOTTOM.equalsIgnoreCase(point)) {
			handleDragBottom(dragTreeReq.getTargetPid(), dragTreeReq.getTargetShowOrder(), dragTreeReq.getSourceId());
		} else {
			throw new Exception("Drag point mush match 'append' 'top' 'bottom'");
		}
	}
	
	/**
	 * 将源节点移动到目标节点的上方
	 * showOrder(source) = showOrder(target) - 1, pid(source) = pid(target)
	 * 
	 * @param targetPid
	 * @param targetShowOrder
	 * @param sourceId
	 */
	private void handleDragTop(Long targetPid, int targetShowOrder, Long sourceId) {
		Authority sourceAuthority = authorityDao.findOne(sourceId);
		sourceAuthority.setParentAuthority(new Authority(targetPid));
		sourceAuthority.setShowOrder(targetShowOrder > 1 ?  targetShowOrder - 1 : 1);
		authorityDao.save(sourceAuthority);
	}
	
	/**
	 * 将源节点移动到目标节点的下方
	 * showOrder(source) = showOrder(target) - 1, pid(source) = pid(target)
	 * 
	 * @param targetPid
	 * @param targetShowOrder
	 * @param sourceId
	 */
	private void handleDragBottom(Long targetPid, int targetShowOrder, Long sourceId) {
		Authority sourceAuthority = authorityDao.findOne(sourceId);
		sourceAuthority.setParentAuthority(new Authority(targetPid));
		sourceAuthority.setShowOrder(targetShowOrder + 1);
		authorityDao.save(sourceAuthority);
	}
	
	/**
	 * 将源节点移动到目标节点内
	 * pid(source) = id(target)
	 * 
	 * @param targetId
	 * @param sourceId
	 */
	private void handleDragAppend(Long targetId, Long sourceId) {
		Authority sourceAuthority = authorityDao.findOne(sourceId);
		Authority parentAuthority = null;
		if(targetId > 0) {
			parentAuthority = new Authority(targetId);
		}
		sourceAuthority.setParentAuthority(parentAuthority);
		authorityDao.save(sourceAuthority);
	}
	
}
