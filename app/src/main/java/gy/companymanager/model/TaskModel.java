package gy.companymanager.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 任务模型
 * Created by lenovo on 2018/3/1.
 */

public class TaskModel extends BmobObject {
    //新任务，已接收，已完成
    private String state;
    //创建人
    private UserModel userid;
    //接收任务人
    private String acceptuserid;
    //开始时间
    private String timestart;
    //结束时间
    private String timeend;
    //任务内容
    private String content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UserModel getUserid() {
        return userid;
    }

    public void setUserid(UserModel userid) {
        this.userid = userid;
    }

    public String getAcceptuserid() {
        return acceptuserid;
    }

    public void setAcceptuserid(String acceptuserid) {
        this.acceptuserid = acceptuserid;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
