package net.xdclass.online_xdclass.mapper;

import net.xdclass.online_xdclass.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 保存用户
     * @param user
     * @return
     */
    int save(User user);

    /**
     * 通过手机号密码找用户
     * @param phone
     * @param pwd
     * @return
     */
    User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    User findByUserId(@Param("user_id") Integer userId);
}
