package gy.companymanager.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 员工模型
 * Created by lenovo on 2018/3/1.
 */

public class UserModel extends BmobObject {
    public UserModel() {
    }

    public UserModel(String userid) {
        this.setObjectId(userid);
    }

    //家庭住址
    private String address;
    //公司职称
    private String companyTitle;
    //请假，离职，在职
    private String state;
    //经理，副经理，普通员工
    private String type;
    //男，女
    private String sex;
    //密码
    private String password;
    //登录名
    private String username;
    //电话
    private String mobilenumber;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}
