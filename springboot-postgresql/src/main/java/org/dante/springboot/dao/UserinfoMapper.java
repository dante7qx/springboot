package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.Userinfo;
import org.dante.springboot.vo.UserBorrowRecord;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 借阅者表 Mapper 接口
 * </p>
 *
 * @author dante
 * @since 2022-07-04
 */
public interface UserinfoMapper extends BaseMapper<Userinfo> {
	
	/**
	 * 查询每个人员的最新借阅记录
	 * 
	 * @return
	 */
	public List<UserBorrowRecord> selectLastUserBorrowRecord();
	
}
