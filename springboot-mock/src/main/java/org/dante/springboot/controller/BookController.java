package org.dante.springboot.controller;

import org.dante.springboot.service.BookService;
import org.dante.springboot.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/query_by_title/{title}")
	public BookVO queryBookByTitle(@PathVariable String title) {
		log.info("queryBookByTitle {}", title);
		return bookService.queryByTitle(title);
	}
	
	@GetMapping("/query_by_id/{id}")
	public BookVO queryBookById(@PathVariable Long id) {
		log.info("queryBookById {}", id);
		return bookService.queryById(id);
	}
	
	
	
}
