package net.xdclass.online_xdclass.service.impl;

import net.xdclass.online_xdclass.model.entity.User;
import net.xdclass.online_xdclass.mapper.UserMapper;
import net.xdclass.online_xdclass.service.UserService;
import net.xdclass.online_xdclass.utils.CommonUtils;
import net.xdclass.online_xdclass.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Map<String, String> userInfo) {
        User user = parseToUser(userInfo);
        if (user != null){
            return userMapper.save(user);
        }
        return -1;
    }

    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        User user = userMapper.findByPhoneAndPwd(phone,CommonUtils.MD5(pwd));

        if (user == null){
            return null;
        }else {
            String token = JWTUtils.geneJsonWebToken(user);
            return token;
        }

    }

    @Override
    public User findByUserId(Integer userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    /**
     * 解析 user 对象
     * @param userInfo
     * @return
     */
    public User parseToUser(Map<String, String> userInfo){
        if (userInfo.containsKey("name")&&userInfo.containsKey("pwd")&&userInfo.containsKey("phone")){
            User user = new User();
            user.setName(userInfo.get("name"));
            user.setPhone(userInfo.get("phone"));
            user.setCreateTime(new Date());
            user.setHeadImg(getRandomHeadImg());
            String pwd = userInfo.get("pwd");
            user.setPwd(CommonUtils.MD5(pwd));
            return user;
        }else {
            return null;
        }
    }


    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cn- beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn- beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn- beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn- beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn- beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"};

    /**
     * 生成随机头像
     * @return
     */
    public String getRandomHeadImg(){
        Random random = new Random();
        int index = random.nextInt(headImg.length);
        return headImg[index];

    }
}
