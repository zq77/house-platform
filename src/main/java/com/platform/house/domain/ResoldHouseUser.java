package com.platform.house.domain;

import javax.persistence.*;

/**
 * Created by Office on 2019/3/10.
 */
@Entity
@Table(name = "tb_resold_house_user")
public class ResoldHouseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tb_resold_house_id")
    private Long resoldHouseId;

    @Column(name = "tb_user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResoldHouseId() {
        return resoldHouseId;
    }

    public void setResoldHouseId(Long resoldHouseId) {
        this.resoldHouseId = resoldHouseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
