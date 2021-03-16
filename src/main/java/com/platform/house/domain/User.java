package com.platform.house.domain;

import com.platform.house.constant.Gender;
import com.platform.house.security.CustomRealm;
import com.platform.house.security.ShiroUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 8221520077874952351L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "nick_name")
    private String nickname;
    
    @Column(name = "real_name")
    private String realname;

    @NotBlank(message = "不能为空")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "不能为空")
    @Column(name = "password")
    private String password;
    
    @Column(name = "unionid")
    private String unionid;
    
    @Column(name = "tim_import")
    private Boolean timImport;

    @Transient
    private String password1;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "update_at")
    private Date updateAt;

    private Boolean status;

    public User() {
        this.requestDate = new Date();
        this.createdAt = new Date();
        this.updateAt = new Date();
        this.status = true;
    }

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long inId) {
        id = inId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String inPassword) {
        password = inPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String inEmail) {
        email = inEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String inPhone) {
        phone = inPhone;
    }


    public Date getRequestDate() {
        return requestDate;
    }


    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRealname() {
        return realname;
    }


    public void setRealname(String realname) {
        this.realname = realname;
    }


    public String getPassword1() {
        return password1;
    }


    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

    public Boolean isTimImport() {
        return timImport;
    }

    public void setTimImport(Boolean timImport) {
        this.timImport = timImport;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String generagePassword(String password) {
        ShiroUser shiroUser = new ShiroUser(this);
        ByteSource credentialsSalt = ByteSource.Util.bytes(shiroUser.getCredentialsSalt());
        return new SimpleHash(CustomRealm.SALT, password, credentialsSalt, CustomRealm.HASH_ITERATION_COUNT).toString();
    }
}
