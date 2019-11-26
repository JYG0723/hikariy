package nuc.jyg.hikariy.service;

import nuc.jyg.hikariy.model.User;

/**
 * @author Ji YongGuang.
 * @date 20:18 2019-04-23.
 * @description
 */
public interface IUserService {

    /**
     * 新增一个用户
     *
     * @param user 用户信息
     * @return 刚新增的用户对象
     */
    User save(User user);

    /**
     * 根据用户id检索对用的用户信息
     *
     * @param id 用户主键id
     * @return 对用的用户对象
     */
    User find(Integer id);

    /**
     * 根据用户名及密码查找用户
     *
     * @return User
     */
    User findByIdAndPassword(Integer id, String password);

    /**
     * 更新用户的密码
     *
     * @param user 更新了新密码的user对象
     * @return 0/1
     */
    User updatePassword(User user);

    /**
     * 根据邮箱和密码检索用户
     *
     * @param email    邮箱
     * @param password 密码
     * @return 用户
     */
    User findByEmailAndPassword(String email, String password);

    /**
     * 根据email获取密保问题
     *
     * @param email 用户的邮箱
     * @return 用户的密保问题
     */
    String findQuestionByEmail(String email);

    /**
     * 根据email来判断对应的question和用户提交的answer是否正确
     *
     * @param email    用户的邮箱
     * @param answer   密保问题的答案
     * @return 0/1
     */
    Boolean checkQuestionAnswer(String email, String answer);

    /**
     * 根据email和新密码来修改用户密码
     *
     * @param email       用户的邮箱
     * @param passwordNew 新密码
     * @return 0/1
     */
    Boolean modifyPassword(String email, String passwordNew);
}
