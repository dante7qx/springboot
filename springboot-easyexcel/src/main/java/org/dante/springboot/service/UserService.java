package org.dante.springboot.service;

import cn.hutool.core.lang.Console;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.dante.springboot.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserService {

    private static final List<UserVO> users = Lists.newArrayList();

    @PostConstruct
    public void init(){
        IntStream.range(0, 100).forEach(i -> {
           UserVO user = new UserVO(Long.valueOf(i + ""), "用户[" + i +"]", 20 + i);
           users.add(user);
        });
    }

    public List<UserVO> list() {
        return users;
    }

    public void saveBatch(List<UserVO> users) {
        Console.log("导入用户 {}", users.size());
    }
}
