package org.dante.springboot.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.dante.springboot.dao.GitDocChapterDAO;
import org.dante.springboot.dao.GitDocDAO;
import org.dante.springboot.po.GitDocPO;
import org.dante.springboot.vo.GitDocVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional(readOnly = true)
public class GitDocService {
	
	@Autowired
	private GitDocDAO gitDocDAO;
	@Autowired
	private GitDocChapterDAO gitDocChapterDAO;
	
	@Transactional
	public GitDocVO persist(GitDocVO vo) {
		return convertPoToVo(gitDocDAO.save(convertVoToPo(vo)));
	}
	
	@Transactional
	public void deleteById(String id) {
		gitDocChapterDAO.deleteByGitDocId(id);
		gitDocDAO.deleteById(id);
	}

	public List<GitDocVO> findGitDocs() {
		return gitDocDAO.findAll().stream().map(d -> convertPoToVo(d)).collect(Collectors.toList());
	}
	
	private GitDocPO convertVoToPo(GitDocVO vo) {
		GitDocPO po = new GitDocPO();
		BeanUtils.copyProperties(vo, po);
		if (StringUtils.isEmpty(po.getId())) {
			po.setId(UUID.randomUUID().toString().toLowerCase());
		}
		return po;
	}

	private GitDocVO convertPoToVo(GitDocPO po) {
		if (po == null)
			return null;
		GitDocVO vo = new GitDocVO();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
}
