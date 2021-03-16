package com.platform.house.services;

import com.alibaba.fastjson.JSONObject;
import com.platform.house.constant.Gender;
import com.platform.house.domain.User;
import com.platform.house.repo.UserRepo;
import com.platform.house.security.CustomRealm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;



    @Transactional
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(user.generagePassword(password));
        userRepo.save(user);
        return user;
    }
    
    /**
     * 将小程序用户保存到数据库
     * @param userInfoJson 小程序用户信息
     * */
    @Transactional
    public User registerWeChatUser(JSONObject userInfoJson) {
    	User user = new User();
		// 用于注册IM(云通讯)用户名支持小写英文字母、数字和下划线，不能是纯数字，长度4-24字节
		Integer lastCountNum = userRepo.getUserCount();
		String eightRandomStr = RandomStringUtils.random(8, "abcdefghijklmnopqrstuvwxyz1234567890");
		user.setUsername("IM" + lastCountNum + "_" + eightRandomStr);
		user.setPassword("IM123456");
		user.setUnionid(userInfoJson.getString("unionId"));
		user.setNickname(userInfoJson.getString("nickName"));
		user.setRealname(userInfoJson.getString("nickName"));
		user.setGender("1".equals(userInfoJson.getString("gender")) ? Gender.MALE : Gender.FEMALE);
		user.setAvatar(userInfoJson.getString("avatarUrl"));
		user = userRepo.save(user);
		return user;
    }
    
    @Transactional
    public void saveAndFlush(User user) {
    	userRepo.saveAndFlush(user);
    }

    public User getUserByUnionid(String unionId) {
        return userRepo.getUserByUnionid(unionId);
    }

    public static User getAccessUserDetail(User user) {
        User returnUser = new User();
        returnUser.setId(user.getId());
        returnUser.setRealname(user.getRealname());
        returnUser.setAvatar(user.getAvatar());
        returnUser.setNickname(user.getNickname());
        return returnUser;
    }
    
    public static void main(String[] args) {
        ByteSource credentialsSalt = ByteSource.Util.bytes("adminMD5");
        Object obj = new SimpleHash(CustomRealm.SALT, "password", credentialsSalt, CustomRealm.HASH_ITERATION_COUNT);
        System.out.println(obj);
    }
}
