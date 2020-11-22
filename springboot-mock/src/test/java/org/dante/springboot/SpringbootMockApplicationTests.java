package org.dante.springboot;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.dante.springboot.dao.BookDAO;
import org.dante.springboot.service.BookService;
import org.dante.springboot.vo.BookVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootMockApplicationTests {
	
	@MockBean
	private BookDAO bookDAO;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	private static final String TITLE = "地球编年史";
	private static final String AUTHOR = "撒迦利亚·西琴";
	
	@Test
    public void queryBookById() {
		Mockito.when(bookDAO.queryById(2000L)).thenReturn(new BookVO(2000L, TITLE, AUTHOR, BigDecimal.valueOf(246.00)));
        try {
			mockMvc.perform(get("/book/query_by_id/2000")
			        .accept(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			   .andExpect(content().string(containsString("\"id\":2000")));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	
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
