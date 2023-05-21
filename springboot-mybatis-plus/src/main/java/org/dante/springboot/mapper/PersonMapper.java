package org.dante.springboot.mapper;

import java.util.List;

import org.dante.springboot.po.PersonPO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PersonMapper extends BaseMapper<PersonPO> {

	public List<PersonPO> selectPersonComplex();
	
	public IPage<PersonPO> selectPageVo(IPage<?> page, PersonPO person);
}
