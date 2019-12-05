package com.secondkill.entity;

import java.io.Serializable;
import java.util.Date;

public class SecKill implements Serializable {

    private static final long serialVersionUID = 3979397085819312989L;
    private int secKillId;//商品id
    private String name;//商品名
    private int stock;//库存
    private Date startTime;
    private Date endTime;

    public int getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(int secKillId) {
        this.secKillId = secKillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SecKill{" +
                "secKillId=" + secKillId +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
