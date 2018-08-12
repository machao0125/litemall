package com.eats.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UserToken {
    private Integer userId;
    private String token;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
