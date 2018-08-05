package com.chao.domain;

import java.util.Date;

public class Mall {
    private Byte id;

    private String sn;

    private String name;

    private Byte state;

    private String longiandlatitude;

    private String address;

    private String describe;

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

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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