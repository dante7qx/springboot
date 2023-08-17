package org.dante.springboot.dao;

import static org.dante.springboot.po.table.AccountTableDef.ACCOUNT;
import static org.dante.springboot.po.table.BookTableDef.BOOK;

import java.util.List;

import org.dante.springboot.SpringbootMybatisFlexApplicationTests;
import org.dante.springboot.po.Book;
import org.dante.springboot.vo.BookVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mybatisflex.core.query.QueryChain;

import cn.hutool.core.lang.Console;

public class BookMapperTests extends SpringbootMybatisFlexApplicationTests {
	
	@Autowired
	private BookMapper bookMapper;
	
	
	@Test
	public void add() {
		Book book = new Book();
		book.setAccountId(1);
		book.setTitle("山海经");
		book.setContent("中国闲人杂谈");
		bookMapper.insert(book);
	}

	@Test
	public void func() {
		List<BookVo> books = QueryChain.of(bookMapper)
			.select(
					BOOK.ALL_COLUMNS, // 图书的所有字段
					ACCOUNT.USER_NAME, // 用户表的 user_name 字段
					ACCOUNT.AGE.as("userAge") // 用户表的 age 字段， as "userAge"
			)
			.from(BOOK)
			.leftJoin(ACCOUNT)
			.on(BOOK.ACCOUNT_ID.eq(ACCOUNT.ID))
			.where(ACCOUNT.ID.le(100))
			.listAs(BookVo.class);

		Console.log(books);
	}
	
}
