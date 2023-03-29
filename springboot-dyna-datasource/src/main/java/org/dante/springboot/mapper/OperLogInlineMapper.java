package org.dante.springboot.mapper;

import java.util.List;

import org.dante.springboot.vo.OperLogInline;

public interface OperLogInlineMapper {

	public int insertOperlogInline(OperLogInline operLog);

	public int batchInsertOperlogInline(List<OperLogInline> operLogs);

	public List<OperLogInline> selectOperLogInlineList(OperLogInline operLog);

	public void cleanOperLogInline();

}
