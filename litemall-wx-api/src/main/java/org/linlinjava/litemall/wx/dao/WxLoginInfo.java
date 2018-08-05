package org.linlinjava.litemall.wx.dao;

public class WxLoginInfo {
    private String code;
    private UserInfo userInfo;
    private String signature;
    private String encrypteData;
    private String iv;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncrypteData() {
        return encrypteData;
    }

    public void setEncrypteData(String encrypteData) {
        this.encrypteData = encrypteData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
