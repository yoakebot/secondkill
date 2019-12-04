package com.secondkill.enums;

public enum SecKillStatusEnum {
    SUCCESS(0, "秒杀成功"),
    REPEAT(1, "重复秒杀"),
    STOCK_LACKING(2, "库存不足或秒杀时间已过"),
    ILLEGAL(3, "非法"),
    EXCEPTION(4, "系统异常");

    SecKillStatusEnum(int status, String info) {
        this.status = status;
        this.info = info;
    }

    private int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
