package gy.companymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.bmob.v3.Bmob;

public class MainActivity extends Activity {
 private TextView main_tv_addemp;//添加员工
    private TextView main_tv_emp;//员工管理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5f049a564688e228b77d16970d95885f");
        setContentView(R.layout.activity_main);
        //初始化视图控件
        main_tv_addemp= (TextView) findViewById(R.id.main_tv_addemp);
        main_tv_emp= (TextView) findViewById(R.id.main_tv_emp);

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
    }
}
