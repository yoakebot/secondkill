package com.secondkill.entity;

import java.io.Serializable;
import java.util.Date;

public class SecKillDetail implements Serializable {

    private static final long serialVersionUID = 677360660914828851L;
    private int secKillId;
    private String userPhone;
    private int status;//秒杀状态
    private Date createTime;
    private SecKill secKill;


    @Override
    public String toString() {
        return "SecKillDetail{" +
                "secKillId=" + secKillId +
                ", userPhone=" + userPhone +
                ", status=" + status +
                ", createTime=" + createTime +
                ", secKill=" + secKill +
                '}';
    }

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }


    public int getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(int secKillId) {
        this.secKillId = secKillId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
