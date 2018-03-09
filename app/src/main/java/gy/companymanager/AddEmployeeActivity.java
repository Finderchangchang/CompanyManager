package gy.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import gy.companymanager.model.UserModel;

public class AddEmployeeActivity extends Activity {

    private EditText emp_et_name;//姓名
    private EditText emp_et_sex;//性别
    private EditText emp_et_zhiwei;//职位
    private EditText emp_et_state;//状态
    private EditText emp_et_address;//地址
    private EditText emp_et_mobile;//电话
    private EditText emp_et_psw;//密码
    private EditText emp_et_psw2;//确认密码
    private EditText emp_et_zhicheng;//公司职称
    private Button btnSave;//保存

    private TextView add_emp_title;//页面标题
    private LinearLayout ll_psw2;//密码
    private LinearLayout ll_psw;//密码
    private LinearLayout ll_address;//住址

    private ImageView btnBack;//返回

    //性别类型
    private String[] usersex;
    //员工类型
    private String[] usertype;
    //员工状态
    private String[] userstate;

    private String userid = "";
    private String isEdit = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        //是否为点击编辑跳转
        userid = getIntent().getStringExtra("userid");
        isEdit = getIntent().getStringExtra("isedit");

        //初始化类型值
        usersex = getResources().getStringArray(R.array.usersex);
        usertype = getResources().getStringArray(R.array.usertype);
        userstate = getResources().getStringArray(R.array.userstate);

        //初始化控件
        add_emp_title = (TextView) findViewById(R.id.add_emp_title);
        emp_et_name = (EditText) findViewById(R.id.emp_et_name);
        emp_et_sex = (EditText) findViewById(R.id.emp_et_sex);
        emp_et_zhiwei = (EditText) findViewById(R.id.emp_et_zhiwei);
        emp_et_state = (EditText) findViewById(R.id.emp_et_state);
        emp_et_mobile = (EditText) findViewById(R.id.emp_et_mobile);
        emp_et_psw = (EditText) findViewById(R.id.emp_et_psw);
        emp_et_psw2 = (EditText) findViewById(R.id.emp_et_psw2);
        emp_et_address = (EditText) findViewById(R.id.emp_et_address);
        emp_et_zhicheng = (EditText) findViewById(R.id.emp_et_zhicheng);

        ll_psw = (LinearLayout) findViewById(R.id.ll_psw);
        ll_psw2 = (LinearLayout) findViewById(R.id.ll_psw2);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);

        btnSave = (Button) findViewById(R.id.emp_add_save);
        btnBack = (ImageView) findViewById(R.id.emp_iv_back);
        //判断是编辑还是详情
        if (!userid.equals("") && isEdit.equals("1")) {
            //显示信息

            add_emp_title.setText("员工信息编辑");
            ll_psw2.setVisibility(View.GONE);
            ll_psw.setVisibility(View.GONE);
            //加载员工信息
            getUserInfo();
            //经理，副经理可看
            SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
            String type = sp.getString("type", null);
            if (type != null && type.equals("普通员工")) {
                ll_address.setVisibility(View.GONE);
            }
        } else if (isEdit.equals("0")) {
            add_emp_title.setText("员工详情");
            getUserInfo();
            //禁用，只是显示信息
            emp_et_sex.setEnabled(false);
            emp_et_name.setEnabled(false);
            emp_et_zhiwei.setEnabled(false);
            emp_et_zhicheng.setEnabled(false);

            emp_et_state.setEnabled(false);
            ll_psw2.setVisibility(View.GONE);
            ll_psw.setVisibility(View.GONE);


            emp_et_address.setEnabled(false);
            emp_et_mobile.setEnabled(false);
            //隐藏密码显示

            btnSave.setVisibility(View.GONE);
        }
        //设置输入框不可编辑，但是响应单击事件
        emp_et_zhiwei.setFocusable(false);
        emp_et_sex.setFocusable(false);
        emp_et_state.setFocusable(false);
        //点击返回，关闭本页
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emp_et_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框选择性别
                new AlertDialog.Builder(AddEmployeeActivity.this)
                        .setTitle("选择性别")
                        .setItems(usersex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                emp_et_sex.setText(usersex[which]);
                                //Toast.makeText(AddEmployeeActivity.this,usersex[which],Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        emp_et_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框选择员工类型
                new AlertDialog.Builder(AddEmployeeActivity.this)
                        .setTitle("选择员工类型")
                        .setItems(usertype, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                emp_et_zhiwei.setText(usertype[which]);
                                //Toast.makeText(AddEmployeeActivity.this,usersex[which],Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        emp_et_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框选择员工状态
                new AlertDialog.Builder(AddEmployeeActivity.this)
                        .setTitle("选择员工状态")
                        .setItems(userstate, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                emp_et_state.setText(userstate[which]);
                                //Toast.makeText(AddEmployeeActivity.this,usersex[which],Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        //保存点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实例化一个员工对象
                UserModel user = new UserModel();
                //获取数据,并赋值给员工对象

                user.setSex(emp_et_sex.getText().toString().trim());
                user.setAddress(emp_et_address.getText().toString().trim());
                user.setCompanyTitle(emp_et_zhicheng.getText().toString().trim());
                user.setState(emp_et_state.getText().toString().trim());
                user.setType(emp_et_zhiwei.getText().toString().trim());
                BmobUser bmobuser=new BmobUser();

                bmobuser.setMobilePhoneNumber(emp_et_mobile.getText().toString().trim());
//                if (!isEdit.equals("1")) {
//                    user.setPassword(emp_et_psw.getText().toString().trim());
//                }
                bmobuser.setUsername(emp_et_name.getText().toString().trim());
                user.setUserid(bmobuser);
                //验证数据
                //验证数据是否输入完整
                if (user.getUserid().getUsername().equals("") || user.getAddress().equals("") || user.getCompanyTitle().equals("") || user.getUserid().getMobilePhoneNumber().equals("")) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else if ((emp_et_psw.getText().toString().trim().equals("") || emp_et_psw2.getText().toString().trim().equals("")) && !isEdit.equals("1")) {
                    //如果不是编辑必须设置登录密码
                    Toast.makeText(AddEmployeeActivity.this, "请设置登录密码", Toast.LENGTH_SHORT).show();
                } else if (user.getUserid().getMobilePhoneNumber().length() != 11) {
                    //手机号长度为11位
                    Toast.makeText(AddEmployeeActivity.this, "请检查手机号码", Toast.LENGTH_SHORT).show();
                } else if (!emp_et_psw.getText().toString().trim().equals(emp_et_psw2.getText().toString().trim())) {

                    //密码是否一致
                    Toast.makeText(AddEmployeeActivity.this, "请检查密码是否一致", Toast.LENGTH_SHORT).show();
                } else {
                    if (isEdit.equals("1")) {
                        //user.setObjectId(userid);
                        user.update(userid,new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddEmployeeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddEmployeeActivity.this, "保存失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        //验证通过可以保存数据
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddEmployeeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddEmployeeActivity.this, "保存失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                        user.save(new SaveListener<UserModel>() {
//                            @Override
//                            public void done(UserModel userModel, BmobException e) {
//
//                            }
//                        });
                    }


                }

            }
        });

    }

    private void getUserInfo() {
        BmobQuery<UserModel> query = new BmobQuery<UserModel>();
        query.getObject(userid, new QueryListener<UserModel>() {

            @Override
            public void done(UserModel object, BmobException e) {
                if (e == null) {
                    //获得playerName的信息
                    //object.getPlayerName();
                    //获得数据的objectId信息
                    object.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    object.getCreatedAt();


                    //显示员工详情
                    emp_et_name.setText(object.getUserid().getUsername());
                    emp_et_sex.setText(object.getSex());
                    emp_et_state.setText(object.getState());
                    emp_et_zhicheng.setText(object.getCompanyTitle());
                    emp_et_zhiwei.setText(object.getType());
                    emp_et_mobile.setText(object.getUserid().getMobilePhoneNumber());
                    emp_et_address.setText(object.getAddress());
                    //emp_et_psw.setText(object.getPassword());
                    //emp_et_psw2.setText(object.getPassword());
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }

        });
    }
}
