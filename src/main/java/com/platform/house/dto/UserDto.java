package com.platform.house.dto;

import java.util.Set;

import com.platform.house.constant.Gender;
import com.platform.house.domain.User;

public class UserDto {
	private Long id;
    private String username;
    private String nickname;
    private String realname;
    private String avatar;
    private String email;
    private String phone;
    private Gender gender;
    private Set<String> roles;
    
    public UserDto(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setNickname(user.getNickname());
        this.setRealname(user.getRealname());
        this.setAvatar(user.getAvatar());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
        this.setGender(user.getGender());
    }
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
    
    
}
