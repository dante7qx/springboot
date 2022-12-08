package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.vo.SvnQueryVO;
import org.dante.springboot.vo.SvnRecordVO;

public interface ISVNService {
	
	public List<SvnRecordVO> getRecord(SvnQueryVO query) throws Exception;
}
