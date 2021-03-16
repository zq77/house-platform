package com.platform.house.domain;

import com.platform.house.constant.DisplayType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Office on 2019/3/23.
 */
@Entity
@Table(name = "tb_mycenter_house")
public class MycenterHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tb_user_id")
    private Long userId;

    @Column(name = "tb_house_id")
    private Long houseId;

    @Column(name = "display_type")
    @Enumerated(EnumType.STRING)
    private DisplayType displayType;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
