package gy.companymanager;

import android.app.Activity;
import android.content.Context;
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
import gy.companymanager.model.UserModel;

//员工信息列表
public class EmployeeListActivity extends Activity {

    private ImageView ivback;//返回
    //private ImageView ivsearch;//查询
    private ListView emp_lv;//员工列表
    private EditText emp_et_searchname;//姓名查询
    private CommonAdapter<UserModel> commonAdapter;
    private List<UserModel> listuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        //初始化页面控件
        ivback = (ImageView) findViewById(R.id.emp_iv_back);
        //ivsearch= (ImageView) findViewById(R.id.emp_iv_search);
        emp_et_searchname = (EditText) findViewById(R.id.emp_et_searchname);

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
        final String type = sp.getString("type", null);
        //在 EditText 上注册该 TextWatcher 实例
        emp_et_searchname.addTextChangedListener(watcher);
        commonAdapter=new CommonAdapter<UserModel>(EmployeeListActivity.this,listuser,R.layout.item_employee) {
            @Override
            public void convert(CommonViewHolder holder, final UserModel userModel, int position) {
                holder.setText(R.id.emp_item_type,"类型:"+userModel.getType());
                holder.setText(R.id.emp_item_zhicheng,"职称:"+userModel.getCompanyTitle());
                holder.setText(R.id.emp_item_state,userModel.getState());
                holder.setText(R.id.emp_item_sex,userModel.getSex());
                holder.setText(R.id.emp_item_name,userModel.getUserid().getUsername());
                //经理，副经理可看

                if (type != null && type.equals("普通员工")) {
                    //ll_address.setVisibility(View.GONE);
                    holder.setVisible(R.id.item_emp_iv_edit,false);
                }
                holder.setOnClickListener(R.id.item_emp_iv_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //跳转到添加页面，进行数据编辑
                        Intent intent=new Intent(EmployeeListActivity.this,AddEmployeeActivity.class);
                        intent.putExtra("userid",userModel.getObjectId());
                        intent.putExtra("isedit","1");
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
                Intent intent=new Intent(EmployeeListActivity.this,AddEmployeeActivity.class);
                intent.putExtra("userid",listuser.get(position).getObjectId());
                intent.putExtra("isedit","0");
                startActivity(intent);
            }
        });
        Search("");

    }

    private void Search(String name) {
        BmobQuery<UserModel> query = new BmobQuery<UserModel>();
        if(!name.equals(""))
        {
            query.addWhereEqualTo("username", name);
        }

        query.findObjects(new FindListener<UserModel>() {
            @Override
            public void done(List<UserModel> object, BmobException e) {
                if (e == null) {
                    //查询成功以后刷新数据
                    listuser = object;
                    commonAdapter.refresh(listuser);
                    //toast("查询用户成功:"+object.size());
                } else {
                    //查询失败
                    Toast.makeText(EmployeeListActivity.this, "查询信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
