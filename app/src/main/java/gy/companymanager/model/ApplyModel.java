package gy.companymanager.model;

import cn.bmob.v3.BmobObject;

/**
 * 申请模型
 * Created by lenovo on 2018/3/3.
 */

public class ApplyModel extends BmobObject {

    private String applycontent;
    //审批备注
    private String applytime;
    //审批时间
    private String applyuserid;
    //审批人
    private String moneytotal;
    //金额
    private String remark;
    //备注
    private String state;
    //通过，未通过
    private String type;
    //物品申领，报销申请，请假申请，加班申请
    private String userid;
    //申请人


    public String getApplycontent() {
        return applycontent;
    }

    public void setApplycontent(String applycontent) {
        this.applycontent = applycontent;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public String getApplyuserid() {
        return applyuserid;
    }

    public void setApplyuserid(String applyuserid) {
        this.applyuserid = applyuserid;
    }

    public String getMoneytotal() {
        return moneytotal;
    }

    public void setMoneytotal(String moneytotal) {
        this.moneytotal = moneytotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
