package org.linlinjava.litemall.db.chao.domain;

import java.util.Date;

public class Mall {
    private int id;

    private String sn;

    private String name;

    private int state;

    private String longiandlatitude;

    private String address;

    private String desc;

    private String picture;

    private String logo;

    private String officetime;

    private Date openingtime;

    private String remark;

    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLongiandlatitude() {
        return longiandlatitude;
    }

    public void setLongiandlatitude(String longiandlatitude) {
        this.longiandlatitude = longiandlatitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOfficetime() {
        return officetime;
    }

    public void setOfficetime(String officetime) {
        this.officetime = officetime;
    }

    public Date getOpeningtime() {
        return openingtime;
    }

    public void setOpeningtime(Date openingtime) {
        this.openingtime = openingtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}