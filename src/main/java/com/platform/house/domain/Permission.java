package com.platform.house.domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * 权限名称
     */
    @Column(name = "perm_name")
    private String name;

    /**
     * 权限描述
     */
    @Column(name = "perm_desc")
    private String desc;

    /**
     * 权限值
     */
    @Column(name = "perm_value")
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
