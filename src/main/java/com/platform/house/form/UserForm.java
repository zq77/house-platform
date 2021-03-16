package com.platform.house.form;

import com.platform.house.constant.Gender;
import com.platform.house.domain.User;

import java.util.Date;

/**
 * Created by Office on 2019/2/24.
 */
public class UserForm {

    private String storeName;
    private String realName;
    private String phone;
    private String password;
    private String newPassword;
    
    private String nickname;
    private String avatar;
    private String email;
    private Gender gender;

    public User toUser() {
        User user = new User();
        mergeUser(user);
        return user;
    }

    public User mergeUser(User user) {
        user.setRealname(this.getRealName());
        user.setNickname(this.getRealName());
        user.setUsername(this.getPhone());
        user.setPhone(this.getPhone());
        user.setGender(Gender.MALE);
        user.setUpdateAt(new Date());
        user.setPassword(user.generagePassword(this.getPassword()));
        return user;
    }
    
    public User mergeUpdatedUser(User user) {
        user.setRealname(this.getRealName());
        user.setPhone(this.getPhone());
        user.setAvatar(this.getAvatar());
        user.setEmail(this.getEmail());
        user.setGender(this.getGender());
        user.setUpdateAt(new Date());
        return user;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
    
    
}
