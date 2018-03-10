package gy.companymanager.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 工作任务模型
 * Created by lenovo on 2018/3/1.
 */

public class TaskModel extends BmobObject {
    //新任务，已接收，已完成
    private String state;
    //创建人
    private UserModel userid;
    //接收任务人
    private UserModel acceptuserid;
    //开始时间
    private String timestart;
    //结束时间
    private String timeend;
    //任务内容
    private String content;
    //任务名称
    private String title;

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

    public UserModel getAcceptuserid() {
        return acceptuserid;
    }

    public void setAcceptuserid(UserModel acceptuserid) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
