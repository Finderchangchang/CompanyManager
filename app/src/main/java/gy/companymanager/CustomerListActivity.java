package gy.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import gy.companymanager.adapter.CommonAdapter;
import gy.companymanager.adapter.CommonViewHolder;
import gy.companymanager.model.Customer;
import gy.companymanager.model.Customer;
import gy.companymanager.model.UserModel;

/**
 * 顾客管理
 */
public class CustomerListActivity extends Activity {

    private ImageView ivback;//返回
    //private ImageView ivsearch;//查询
    private ListView emp_lv;//员工列表
    private EditText emp_et_searchname;//姓名查询
    private CommonAdapter<Customer> commonAdapter;
    private List<Customer> listuser;
    private String type;//登录用户类型
    private String user_id;//当前登录的用户id
    private EditText cus_et_searchtype;//按客户类型查询
private String[]custype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        //初始化页面控件
        ivback = (ImageView) findViewById(R.id.emp_iv_back);
        custype=getResources().getStringArray(R.array.customertypesearch);
        emp_et_searchname = (EditText) findViewById(R.id.emp_et_searchname);
        cus_et_searchtype= (EditText) findViewById(R.id.cus_et_searchtype);
        cus_et_searchtype.setFocusable(false);
        cus_et_searchtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框选择客户类型、
                new AlertDialog.Builder(CustomerListActivity.this)
                        .setTitle("选择客户状态")
                        .setItems(custype, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                cus_et_searchtype.setText(custype[which]);
                                Search(emp_et_searchname.getText().toString().trim());
                            }
                        }).show();
            }
        });
        emp_lv = (ListView) findViewById(R.id.emp_lv);
        //点击返回，关闭本页
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
        type = sp.getString("type", null);
        user_id = sp.getString("objectid", null);
        //在 EditText 上注册该 TextWatcher 实例
        emp_et_searchname.addTextChangedListener(watcher);
        commonAdapter = new CommonAdapter<Customer>(CustomerListActivity.this, listuser, R.layout.item_employee) {
            @Override
            public void convert(CommonViewHolder holder, final Customer Customer, int position) {
                holder.setText(R.id.emp_item_type, Customer.getCompanyname());
//                holder.setText(R.id.emp_item_zhicheng, "职称:" + Customer.getCompanytitle());
                holder.setText(R.id.emp_item_state, Customer.getType());
                holder.setText(R.id.emp_item_sex, Customer.getMobilephone());
                holder.setText(R.id.emp_item_name, Customer.getName());
                holder.setOnClickListener(R.id.item_emp_iv_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加页面，进行数据编辑
                        Intent intent = new Intent(CustomerListActivity.this, AddCustomerActivity.class);
                        intent.putExtra("userid", Customer.getObjectId());
                        intent.putExtra("isedit", "1");
                        startActivity(intent);
                    }
                });

            }
        };
        emp_lv.setAdapter(commonAdapter);
        emp_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到添加页面，查看详情
                Intent intent = new Intent(CustomerListActivity.this, AddCustomerActivity.class);
                intent.putExtra("userid", listuser.get(position).getObjectId());
                intent.putExtra("isedit", "0");
                startActivity(intent);
            }
        });
        Search("");

    }

    private void Search(String name) {
        BmobQuery<Customer> query = new BmobQuery<Customer>();
        if (!name.equals("")) {
            query.addWhereEqualTo("name", name);
        }
        //如果为普通员工只显示自己的客户，其他显示所有
        if (("普通员工").equals(type)) {
            query.addWhereEqualTo("user", new UserModel(user_id));
        }
        if(!cus_et_searchtype.getText().toString().equals("全部")){
            query.addWhereEqualTo("type", cus_et_searchtype.getText().toString());
        }

        query.findObjects(new FindListener<Customer>() {
            @Override
            public void done(List<Customer> object, BmobException e) {
                if (e == null) {
                    //查询成功以后刷新数据
                    listuser = object;
                    commonAdapter.refresh(listuser);
                    //toast("查询用户成功:"+object.size());
                } else {
                    //查询失败
                    Toast.makeText(CustomerListActivity.this, "查询信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //按输入的姓名进行查询相关信息
            Search(emp_et_searchname.getText().toString().trim());
        }
    };
}
