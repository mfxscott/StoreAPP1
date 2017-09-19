package com.xianhao365.o2o.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户个人信息
 * @author mfx
 * @time  2017/8/4 10:37
 */
public class UserInfoEntity implements Parcelable {
    private  String birthday;
    private  String icon;
    private  String sex;
    private  String acount;
    private  String username;
    private  String nickname;
    private  String email;
    private  String tag;
    private String grade;
    private  String mobile;
    //店铺相关属性
    private  String shopLogo;
    private String district;
    private String province;
    private String manager;
    private String city;
    private String addr;
    private String shopName;

    protected UserInfoEntity(Parcel in) {
        birthday = in.readString();
        icon = in.readString();
        sex = in.readString();
        acount = in.readString();
        username = in.readString();
        nickname = in.readString();
        email = in.readString();
        tag = in.readString();
        grade = in.readString();
        mobile = in.readString();
        shopLogo = in.readString();
        district = in.readString();
        province = in.readString();
        manager = in.readString();
        city = in.readString();
        addr = in.readString();
        shopName = in.readString();
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel in) {
            return new UserInfoEntity(in);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(birthday);
        dest.writeString(icon);
        dest.writeString(sex);
        dest.writeString(acount);
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(email);
        dest.writeString(tag);
        dest.writeString(grade);
        dest.writeString(mobile);
        dest.writeString(shopLogo);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeString(manager);
        dest.writeString(city);
        dest.writeString(addr);
        dest.writeString(shopName);
    }
}
