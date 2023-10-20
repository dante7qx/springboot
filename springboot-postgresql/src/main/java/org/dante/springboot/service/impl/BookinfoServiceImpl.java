package org.dante.springboot.service.impl;

import org.dante.springboot.dao.BookinfoMapper;
import org.dante.springboot.po.Bookinfo;
import org.dante.springboot.service.BookinfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 借阅者表 服务实现类
 * </p>
 *
 * @author dante
 * @since 2022-07-04
 */
@Service
public class BookinfoServiceImpl extends ServiceImpl<BookinfoMapper, Bookinfo> implements BookinfoService {

}
