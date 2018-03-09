package gy.companymanager.model;

import cn.bmob.v3.BmobObject;

/**
 * 客户模型
 * Created by lenovo on 2018/3/2.
 */

public class Customer extends BmobObject {
    //地址
    private String address;
    //新增客户，老客户，潜在客户
    private String type;
    //电话
    private String mobilephone;
    //姓名
    private String name;
    //公司名称
    private String companyname;
    //职位
    private String companytitle;
    //备注
    private String remark;
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanytitle() {
        return companytitle;
    }

    public void setCompanytitle(String companytitle) {
        this.companytitle = companytitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
