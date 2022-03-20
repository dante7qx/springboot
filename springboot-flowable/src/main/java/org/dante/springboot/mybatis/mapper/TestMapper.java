package org.dante.springboot.mybatis.mapper;

import java.util.List;

import org.dante.springboot.mybatis.vo.TestVO;

public interface TestMapper {
	
	public List<TestVO> queryDeployProcess();
}
