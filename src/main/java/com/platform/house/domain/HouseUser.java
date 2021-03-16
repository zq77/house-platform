package com.platform.house.domain;

import javax.persistence.*;

/**
 * Created by Office on 2019/3/10.
 */
@Entity
@Table(name = "tb_house_user")
public class HouseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tb_house_id")
    private Long houseId;

    @ManyToOne
    @JoinColumn(name = "tb_user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
