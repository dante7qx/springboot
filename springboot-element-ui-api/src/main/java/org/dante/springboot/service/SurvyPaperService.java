package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dao.SurvyPaperDAO;
import org.dante.springboot.entity.SurvyPaper;
import org.dante.springboot.entity.SurvyPaperUser;
import org.dante.springboot.mapper.SurvyPaperMapper;
import org.dante.springboot.mapper.SurvyPaperUserMapper;
import org.dante.springboot.po.SurvyPaperPO;
import org.dante.springboot.vo.SurvyPaperVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

@Service
@Transactional(readOnly = true)
public class SurvyPaperService {
	
	private final SurvyPaperDAO survyPaperDAO;
	private final SurvyPaperMapper survyPaperMapper;
	private final SurvyPaperUserMapper survyPaperUserMapper;
	
	public SurvyPaperService(SurvyPaperDAO survyPaperDAO, SurvyPaperMapper survyPaperMapper, SurvyPaperUserMapper survyPaperUserMapper) {
		this.survyPaperDAO = survyPaperDAO;
		this.survyPaperMapper = survyPaperMapper;
		this.survyPaperUserMapper = survyPaperUserMapper;
		
	}
	
	public SurvyPaperVO getById(Long id) {
		SurvyPaperPO po = survyPaperDAO.getReferenceById(id);
		SurvyPaperVO vo = new SurvyPaperVO();
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
	
	
	/*============================================ Mbybatis ============================================*/
	public SurvyPaper findById(Long id) {
		return survyPaperMapper.findById(id);
	}
	
	public List<SurvyPaper> findList(SurvyPaper survyPaper) {
		return survyPaperMapper.selectSurvyPaperList(survyPaper);
	}
	
	@Transactional
	public int persistSurvyPaper(SurvyPaper survyPaper) {
		if(survyPaper.getId() > 0) {
			survyPaper.setUpdateTime(DateUtil.date());
			return survyPaperMapper.updateSurvyPaper(survyPaper);
		} else {
			survyPaper.setCreateTime(DateUtil.date());
			return survyPaperMapper.insertSurvyPaper(survyPaper);
		}
	}
	
	@Transactional
	public SurvyPaperVO deleteSurvyPaper(Long id) {
		survyPaperDAO.deleteById(id);
//		survyPaperMapper.deleteSurvyPage(id);
		return new SurvyPaperVO(id);
	}
	
	public SurvyPaperUser findByPaperAndUserById(Long id) {
		return survyPaperUserMapper.selectSurvyPaperUserById(id);
	}
	
	public SurvyPaperUser findByPaperAndUser(Long paperId, Long userId) {
		SurvyPaperUser result = new SurvyPaperUser();
		SurvyPaperUser paperUser = new SurvyPaperUser();
		paperUser.setPaperId(paperId);
		paperUser.setUserId(userId);
		List<SurvyPaperUser> list = survyPaperUserMapper.selectSurvyPaperUserList(paperUser);
		if(CollUtil.isNotEmpty(list)) {
			result = list.get(0);
		}
		return result;
	}
	
	@Transactional
	public int persistSurvyPaperUser(SurvyPaperUser survyPaperUser) {
		if(survyPaperUser.getId() != null) {
			survyPaperUser.setUpdateTime(DateUtil.date());
			return survyPaperUserMapper.updateSurvyPaperUser(survyPaperUser);
		} else {
			survyPaperUser.setCreateTime(DateUtil.date());
			return survyPaperUserMapper.insertSurvyPaperUser(survyPaperUser);
		}
	}
	
}
