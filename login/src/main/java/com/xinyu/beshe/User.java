package com.xinyu.beshe;

/**
 * @作者 miaoxinyu on 2018/1/29.
 * @描述 应用版本信息
 */
public class User {

    //有3个属性 昵称 密码 电话号码
    private int id;
    private String nickname;
    private String phone_number;
    private String password;

    public User(int mid, String mnickname, String mphone_number, String mpassword) {
        id=mid;
        nickname=mnickname;
        password=mpassword;
        phone_number=mphone_number;
    }

    public User(String mnickname, String mphone_number, String mpassword) {
        //id=mid;
        nickname=mnickname;
        password=mpassword;
        phone_number=mphone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {

        return "Database{" +
                "nickname='" + nickname + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
