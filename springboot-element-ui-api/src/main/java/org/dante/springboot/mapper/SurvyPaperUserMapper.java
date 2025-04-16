package org.dante.springboot.mapper;

import java.util.List;

import org.dante.springboot.entity.SurvyPaperUser;

public interface SurvyPaperUserMapper {
	
	public List<SurvyPaperUser> selectSurvyPaperUserList(SurvyPaperUser survyPaperUser);
	
	public SurvyPaperUser selectSurvyPaperUserById(Long id);
	
	public int insertSurvyPaperUser(SurvyPaperUser survyPaperUser);
	
	
	public int updateSurvyPaperUser(SurvyPaperUser survyPaperUser);
	
	public int deleteSurvyPagerUser(Long id);
	
}
