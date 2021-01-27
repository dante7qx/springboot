package org.dante.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.dante.springboot.dao.GitDocChapterDAO;
import org.dante.springboot.po.GitDocChapterPO;
import org.dante.springboot.vo.GitDocChapterVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class GitDocChapterService {
	
	@Autowired
	private GitDocChapterDAO gitDocChapterDAO;
	
	@Transactional
	public GitDocChapterVO persist(GitDocChapterVO vo) {
		return convertPoToVo(gitDocChapterDAO.save(convertVoToPo(vo)));
	}

	public List<GitDocChapterVO> findGitDocChapters(String gitDocId) {
		List<GitDocChapterPO> rootPOs = gitDocChapterDAO.findRoot(gitDocId);
		if(CollectionUtils.isEmpty(rootPOs)) {
			return new ArrayList<GitDocChapterVO>();
		}
		return rootPOs.stream().map(d -> buildDocChapterTree(d)).collect(Collectors.toList());
	}
	
	private GitDocChapterVO buildDocChapterTree(GitDocChapterPO po) {
		GitDocChapterVO vo = convertPoToVo(po);
		List<GitDocChapterPO> childPOs = gitDocChapterDAO.findByPid(vo.getId());
		if(!CollectionUtils.isEmpty(childPOs)) {
			childPOs.forEach(c -> {
				GitDocChapterVO cvo = convertPoToVo(c);
				vo.addChildren(cvo);
				buildDocChapterTree(c);
				
			});
		}
		return vo;
	}
	
	
	private GitDocChapterPO convertVoToPo(GitDocChapterVO vo) {
		GitDocChapterPO po = new GitDocChapterPO();
		BeanUtils.copyProperties(vo, po, new String[] {"children"});
		if (StringUtils.isEmpty(po.getId())) {
			po.setId(UUID.randomUUID().toString().toLowerCase());
		}
		return po;
	}

	private GitDocChapterVO convertPoToVo(GitDocChapterPO po) {
		if (po == null)
			return null;
		GitDocChapterVO vo = new GitDocChapterVO();
		BeanUtils.copyProperties(po, vo, new String[] {"children"});
		return vo;
	}
}
