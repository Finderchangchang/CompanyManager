package gy.companymanager.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 员工模型
 * Created by lenovo on 2018/3/1.
 */

public class UserModel extends BmobObject {
    public UserModel() {
        this.setTableName("_User");
    }
    private BmobUser userid;
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

    public BmobUser getUserid() {
        return userid;
    }

    public void setUserid(BmobUser userid) {
        this.userid = userid;
    }
}
