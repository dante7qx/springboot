package org.dante.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.dante.springboot.entity.SurvyPaper;

public interface SurvyPaperMapper {
	
	@Select("select id, type, paper_info, create_by, create_time, update_by, update_time from spi_survy_paper where id = #{id}")
	public SurvyPaper findById(Long id);
	
	public List<SurvyPaper> selectSurvyPaperList(SurvyPaper survyPaper);
	
	
	public int insertSurvyPaper(SurvyPaper survyPaper);
	
	
	public int updateSurvyPaper(SurvyPaper survyPaper);
	
	public int deleteSurvyPagerUser(Long id);
	
}
