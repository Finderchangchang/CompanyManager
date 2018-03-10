package gy.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import gy.companymanager.model.Customer;
import gy.companymanager.model.Customer;
import gy.companymanager.model.UserModel;

public class AddCustomerActivity extends Activity {
    private EditText cus_et_name;//姓名
    private EditText cus_et_campany;//性别
    private EditText cus_et_zhiwei;//职位
    private EditText cus_et_state;//状态
    private EditText cus_et_address;//地址
    private EditText cus_et_mobile;//电话
    private EditText jd_et;//进度
    private Button btnSave;//保存
    private TextView add_cus_title;//顶部文字
    //员工类型
    private String[] usertype;
    //客户状态
    private String[] customertype;
    private String isEdit = "0";//true：编辑。false：添加
    private String userid = "";//需要编辑的客户id
    private String objectid = "";//当前登录账户的id
    private ImageView btnBack;//返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
        objectid = sp.getString("objectid", null);//读取缓存数据
        userid = getIntent().getStringExtra("userid");
        isEdit = getIntent().getStringExtra("isedit");
        //初始化类型值
        usertype = getResources().getStringArray(R.array.usertype);
        customertype = getResources().getStringArray(R.array.customertype);

        //初始化控件
        cus_et_name = (EditText) findViewById(R.id.cus_et_name);
        cus_et_campany = (EditText) findViewById(R.id.cus_et_campany);
        cus_et_zhiwei = (EditText) findViewById(R.id.cus_et_zhiwei);
        cus_et_state = (EditText) findViewById(R.id.cus_et_state);
        cus_et_mobile = (EditText) findViewById(R.id.cus_et_mobile);
        add_cus_title = (TextView) findViewById(R.id.add_cus_title);
        cus_et_address = (EditText) findViewById(R.id.cus_et_address);
        btnSave = (Button) findViewById(R.id.cus_add_save);
        jd_et = (EditText) findViewById(R.id.jd_et);
        btnBack = (ImageView) findViewById(R.id.cus_iv_back);
        //点击返回，关闭本页
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //职位点击事件

        cus_et_state.setFocusable(false);
        cus_et_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddCustomerActivity.this)
                        .setTitle("选择客户状态")
                        .setItems(customertype, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                cus_et_state.setText(customertype[which]);
                            }
                        }).show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实例化一个员工对象
                Customer user = new Customer();
                //获取数据,并赋值给员工对象
                user.setAddress(cus_et_address.getText().toString().trim());
                user.setType(cus_et_state.getText().toString().trim());
                user.setName(cus_et_name.getText().toString().trim());
                user.setCompanyname(cus_et_campany.getText().toString().trim());
                user.setCompanytitle(cus_et_zhiwei.getText().toString().trim());
                user.setRemark(jd_et.getText().toString().trim());
                user.setMobilephone(cus_et_mobile.getText().toString().trim());
                user.setUser(new UserModel(objectid));
                //验证数据
                //验证数据是否输入完整
                if (user.getName().equals("") || user.getAddress().equals("") || user.getCompanytitle().equals("") || user.getMobilephone().equals("")) {
                    Toast.makeText(AddCustomerActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else if (user.getMobilephone().length() != 11) {
                    //手机号长度为11位
                    Toast.makeText(AddCustomerActivity.this, "请检查手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (userid == null || userid.equals("")) {
                        //验证通过可以保存数据
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddCustomerActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddCustomerActivity.this, "保存失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        //执行修改
                        user.update(userid, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddCustomerActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddCustomerActivity.this, "保存失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
        get_edit_detail();
    }

    public void get_edit_detail() {
        //判断是编辑还是详情
        if (userid != null && (!userid.equals("")) && isEdit != null && isEdit.equals("1")) {
            //显示信息

            add_cus_title.setText("客户信息编辑");
            //加载员工信息
            getUserInfo();

        } else if (isEdit != null && isEdit.equals("0")) {
            add_cus_title.setText("客户详情");
            getUserInfo();
            //禁用，只是显示信息
            cus_et_name.setEnabled(false);
            jd_et.setEnabled(false);
            cus_et_campany.setEnabled(false);
            cus_et_state.setEnabled(false);


            cus_et_address.setEnabled(false);
            cus_et_mobile.setEnabled(false);
            //隐藏密码显示

            btnSave.setVisibility(View.GONE);
        }
    }

    //获取客户信息信息
    private void getUserInfo() {
        BmobQuery<Customer> query = new BmobQuery<Customer>();
        query.getObject(userid, new QueryListener<Customer>() {

            @Override
            public void done(Customer object, BmobException e) {
                if (e == null) {
                    //显示员工详情
                    cus_et_name.setText(object.getName());
                    cus_et_state.setText(object.getType());
                    cus_et_zhiwei.setText(object.getCompanytitle());
                    cus_et_mobile.setText(object.getMobilephone());
                    jd_et.setText(object.getRemark());
                    cus_et_campany.setText(object.getCompanyname());
                    cus_et_address.setText(object.getAddress());
                    //cus_et_psw.setText(object.getPassword());
                    //cus_et_psw2.setText(object.getPassword());
                } else {

                    Toast.makeText(AddCustomerActivity.this, "获取用户信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    finish();
                }
            }

        });
    }
}
