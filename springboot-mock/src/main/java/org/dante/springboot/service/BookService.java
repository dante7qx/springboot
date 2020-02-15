package org.dante.springboot.service;

import org.dante.springboot.vo.BookVO;

public interface BookService {

	public BookVO queryByTitle(String title);

}
