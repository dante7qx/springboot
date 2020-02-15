package org.dante.springboot;

import java.math.BigDecimal;

import org.dante.springboot.dao.BookDAO;
import org.dante.springboot.service.BookService;
import org.dante.springboot.vo.BookVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootMockApplicationTests {
	
	@MockBean
	private BookDAO bookDAO;
	
	@Autowired
	private BookService bookService;
	
	private static final String TITLE = "地球编年史";
	private static final String AUTHOR = "撒迦利亚·西琴";
	
	@Test
	public void queryByTitle() {
		// 定义当调用Mock bookDAO 的 queryByTitle 方法，参数为地球编年史，返回 Id=1001L的图书
		Mockito.when(bookDAO.queryByTitle(TITLE)).thenReturn(new BookVO(1001L, TITLE, AUTHOR, BigDecimal.valueOf(246.00)));
		BookVO book = bookService.queryByTitle(TITLE);
		Assert.assertNotNull(book);
        Assert.assertEquals(book.getId(), new Long(1001L));
        Assert.assertEquals(book.getAuthor(), AUTHOR);
	}
	
	@Test
	public void queryByTitle2() {
		Mockito.when(bookDAO.queryByTitle(Mockito.anyString())).thenReturn(new BookVO(1001L, TITLE, AUTHOR, BigDecimal.valueOf(246.00)));
		BookVO book = bookService.queryByTitle("任意书名");
		Assert.assertNotNull(book);
        Assert.assertEquals(book.getId(), new Long(1001L));
        Assert.assertEquals(book.getAuthor(), AUTHOR);
	}
	
	@Test(expected = RuntimeException.class)
	public void queryByTitle3() {
		Mockito.when(bookDAO.queryByTitle("宪法")).thenThrow(new RuntimeException("mock throw exception"));
		BookVO book = bookService.queryByTitle("宪法");
		Assert.assertNotNull(book);
        Assert.assertEquals(book.getId(), new Long(1001L));
        Assert.assertEquals(book.getAuthor(), AUTHOR);
	}
}
