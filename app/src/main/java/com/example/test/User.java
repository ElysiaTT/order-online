package com.example.test;

/**
 * 用户数据模型
 */
public class User {
    private String userId;           // 用户ID
    private String username;         // 用户名
    private String password;         // 密码（实际项目中应该加密存储）
    private String phoneNumber;      // 手机号
    private String email;            // 邮箱
    private String avatarUrl;        // 头像URL

    public User(String userId, String username, String password, String phoneNumber, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.avatarUrl = "default_avatar"; // 默认头像
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 获取脱敏后的手机号
     * @return 脱敏手机号（例如：138****1234）
     */
    public String getMaskedPhoneNumber() {
        if (phoneNumber == null || phoneNumber.length() < 11) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }

    /**
     * 获取脱敏后的邮箱
     * @return 脱敏邮箱（例如：xxx@123.com）
     */
    public String getMaskedEmail() {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        return "***@" + parts[1];
    }
}
