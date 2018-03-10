package gy.companymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.bmob.v3.Bmob;

public class MainActivity extends Activity {
 private TextView main_tv_addemp;//添加员工
    private TextView main_tv_emp;//员工管理
    private TextView main_tv_addtask;//添加任务
    private TextView add_cus_tv;//添加客户
    private TextView manage_cus_tv;//客户管理
    private TextView main_tv_taskmanager;//任务管理
    private String type;//登录用户类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5f049a564688e228b77d16970d95885f");
        setContentView(R.layout.activity_main);
        //初始化视图控件
        main_tv_addemp= (TextView) findViewById(R.id.main_tv_addemp);
        main_tv_emp= (TextView) findViewById(R.id.main_tv_emp);
        main_tv_addtask= (TextView) findViewById(R.id.main_tv_addtask);
        manage_cus_tv = (TextView) findViewById(R.id.manage_cus_tv);
        add_cus_tv = (TextView) findViewById(R.id.add_cus_tv);
        main_tv_taskmanager= (TextView) findViewById(R.id.main_tv_taskmanager);


        //根据登录用户类型，显示隐藏模块
        SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
        type = sp.getString("type", null);

        if(type.equals("普通员工")){
            main_tv_addemp.setVisibility(View.GONE);
            main_tv_addtask.setVisibility(View.GONE);

        }
        main_tv_addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入添加工作任务界面
                Intent intent=new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(intent);
            }
        });
        main_tv_taskmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到任务管理界面
                Intent intent=new Intent(MainActivity.this,TaskListActivty.class);
                startActivity(intent);
            }
        });
        //点击添加员工
        main_tv_addemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入添加员工界面张三
                Intent intent=new Intent(MainActivity.this,AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
        main_tv_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入添加员工管理界面
                Intent intent=new Intent(MainActivity.this,EmployeeListActivity.class);
                startActivity(intent);
            }
        });
        //点击添加客户信息
        add_cus_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
        //点击进入客户管理
        manage_cus_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerListActivity.class);
                startActivity(intent);
            }
        });
    }
}
