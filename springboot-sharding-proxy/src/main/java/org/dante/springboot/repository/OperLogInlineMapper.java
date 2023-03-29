package org.dante.springboot.repository;

import java.util.List;

import org.dante.springboot.entity.OperLogInline;

public interface OperLogInlineMapper {
	
	public int insertOperlogInline(OperLogInline operLog);
	
	public int batchInsertOperlogInline(List<OperLogInline> operLogs);
	
	public List<OperLogInline> selectOperLogInlineList(OperLogInline operLog);
	
	public void cleanOperLogInline();
	
	public int dropOperLogInline();
	
}
