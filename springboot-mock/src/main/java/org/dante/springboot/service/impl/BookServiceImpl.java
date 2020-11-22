package org.dante.springboot.service.impl;

import org.dante.springboot.dao.BookDAO;
import org.dante.springboot.service.BookService;
import org.dante.springboot.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDAO;

	@Override
	public BookVO queryByTitle(String title) {
		return bookDAO.queryByTitle(title);
	}

	@Override
	public BookVO queryById(Long id) {
		return bookDAO.queryById(id);
	}

}
