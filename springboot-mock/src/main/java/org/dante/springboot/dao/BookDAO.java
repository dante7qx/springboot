package org.dante.springboot.dao;

import org.dante.springboot.vo.BookVO;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {
	
	/**
	 * 根据书名查询图书（尚未完成）
	 * 
	 * @param title
	 * @return
	 */
	public BookVO queryByTitle(String title) {
		return null;
	}
	
	public BookVO queryById(Long id) {
		return null;
	}
}
