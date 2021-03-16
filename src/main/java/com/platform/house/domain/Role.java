package com.platform.house.domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * 角色名称，用于显示
     */
    @Column(name = "role_name")
    private String name;

    /**
     * 角色描述
     */
    @Column(name = "role_desc")
    private String desc;

    /**
     * 角色值，用于权限判断
     */
    @Column(name = "role_value")
    private String value;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
