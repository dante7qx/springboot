package org.dante.springboot.service;

import org.dante.springboot.vo.UserInfoVO;

public interface UserInfoService {
	/**
     * 增加用户信息
     *
     * @param userInfoVO 用户信息
     */
	UserInfoVO addUserInfo(UserInfoVO userInfoVO);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfoVO getByName(Integer id);

    /**
     * 修改用户信息
     *
     * @param userInfoVO 用户信息
     * @return 用户信息
     */
    UserInfoVO updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     */
    void deleteById(Integer id);
}
