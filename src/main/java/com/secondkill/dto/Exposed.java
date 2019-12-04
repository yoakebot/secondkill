package com.secondkill.dto;

import java.util.Date;

public class Exposed {

    private String Md5;
    private boolean flag;
    private Date nowTime;
    private Date startTime;
    private Date endTime;

    public String getMd5() {
        return Md5;
    }

    public void setMd5(String md5) {
        Md5 = md5;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
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
}
