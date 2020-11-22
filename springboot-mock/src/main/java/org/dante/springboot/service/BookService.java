package org.dante.springboot.service;

import org.dante.springboot.vo.BookVO;

public interface BookService {

	public BookVO queryById(Long id);
	
	public BookVO queryByTitle(String title);

}
