package com.platform.house.security;

import java.util.Set;

import com.platform.house.constant.Gender;
import com.platform.house.domain.User;

public class ShiroUser {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String realname;
    private String avatar;
    private String email;
    private String phone;
    private Gender gender;

    /**
     * 用户所有角色值，用于shiro做角色权限的判断
     */
    private Set<String> roles;

    /**
     * 用户所有权限值，用于shiro做资源权限的判断
     */
    private Set<String> perms;

    public ShiroUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.realname = user.getRealname();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.gender = user.getGender();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRealname() {
        return realname;
    }
    
    public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Gender getGender() {
		return gender;
	}

	public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public String getCredentialsSalt() {
        return this.username + CustomRealm.SALT;
    }

    public String getAvatar() {
        return avatar;
    }

    
}
