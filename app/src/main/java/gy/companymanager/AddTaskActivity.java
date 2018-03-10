package gy.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import gy.companymanager.model.TaskModel;
import gy.companymanager.model.UserModel;

//添加工作任务
public class AddTaskActivity extends Activity {

    private ImageView task_iv_back;//返回

    private EditText task_et_name;//任务名称
    private EditText task_et_accept;//指定任务人
    private EditText task_et_content;//任务内容
    private EditText task_et_timestart;//任务开始时间
    private EditText task_et_timeend;//任务结束时间

    private List<UserModel> listuser;//存储所有普通员工信息，用于选择指定任务人
    private String[] struser;//数组用于选择显示
    private String userObjectid;//指定任务人id

    private int mYear;//当前年月日
    private int mMonth;
    private int mDay;
    private int isstart = 1;//记录点击的是开始时间还是结束时间
    private String userid;//登录用户的ID

    private Button task_add_save;//保存信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //视图控件初始化
        task_iv_back = (ImageView) findViewById(R.id.task_iv_back);
        task_et_name = (EditText) findViewById(R.id.task_et_name);
        task_et_accept = (EditText) findViewById(R.id.task_et_accept);
        task_et_content = (EditText) findViewById(R.id.task_et_content);
        task_et_timestart = (EditText) findViewById(R.id.task_et_timestart);
        task_et_timeend = (EditText) findViewById(R.id.task_et_timeend);
        task_add_save = (Button) findViewById(R.id.task_add_save);
        SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
        userid = sp.getString("objectid", null);


        //设置输入框不可编辑，但是响应单击事件
        task_et_accept.setFocusable(false);
        task_et_timestart.setFocusable(false);
        task_et_timeend.setFocusable(false);
        //设置默认时间
        //String nowtime = new Date().toString();
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");

        task_et_timestart.setText(df.format(new Date()));
        task_et_timeend.setText(df.format(new Date()));
        getUsers();
        //点击选择任务人
        task_et_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框选择性别
                new AlertDialog.Builder(AddTaskActivity.this)
                        .setTitle("选择完成任务人")
                        .setItems(struser, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                task_et_accept.setText(struser[which]);
                                //记录选择的任务的唯一Id
                                userObjectid = listuser.get(which).getObjectId();
                            }
                        }).show();
            }
        });
        //任务结束时间
        task_et_timeend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录点击开始还是结束时间
                isstart = 0;
                //弹出日期选择框
                new DatePickerDialog(AddTaskActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        //任务开始时间
        task_et_timestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录点击开始还是结束时间
                isstart = 1;
                //弹出日期选择框
                new DatePickerDialog(AddTaskActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
            }
        });
        //点击返回，关闭本页
        task_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击保存按钮
        task_add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取页面数据
                TaskModel task = new TaskModel();
                task.setContent(task_et_content.getText().toString().trim());
                task.setState("新任务");
                task.setTimeend(task_et_timeend.getText().toString().trim());
                task.setTimestart(task_et_timestart.getText().toString().trim());
                task.setTitle(task_et_name.getText().toString().trim());
                UserModel user=new UserModel(userid);
                task.setUserid(user);
                //验证数据
                if(task.getContent().equals("")||task.getTitle().equals("")){
                    Toast.makeText(AddTaskActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }else{
                    //保存数据
                    task.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddTaskActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddTaskActivity.this, "保存失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
    }

    //获取所有普通员工信息
    private void getUsers() {
        BmobQuery<UserModel> query = new BmobQuery<UserModel>();
        query.addWhereEqualTo("type", "普通员工");
        query.findObjects(new FindListener<UserModel>() {
            @Override
            public void done(List<UserModel> object, BmobException e) {
                if (e == null) {
                    listuser = object;
                    //查询成功以后刷新数据
                    struser = new String[object.size()];
                    //遍历集合，把姓名存入数组
                    for (int i = 0; i < object.size(); i++) {
                        struser[i] = object.get(i).getUsername();
                    }
                    task_et_accept.setText(listuser.get(0).getUsername());
                    userObjectid=listuser.get(0).getObjectId();
                } else {
                    //查询失败
                    Toast.makeText(AddTaskActivity.this, "查询信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            //记录点击开始还是结束时间
            if (isstart == 1) {
                task_et_timestart.setText(days);
            } else {
                task_et_timeend.setText(days);
            }
        }
    };

}
