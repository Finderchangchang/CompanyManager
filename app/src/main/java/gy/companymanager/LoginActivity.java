package gy.companymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import gy.companymanager.model.UserModel;

///登录
public class LoginActivity extends Activity {

    private EditText etusername;//用户名
    private EditText etuserpwd;//用户密码
    private Button btnLogin;//登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5f049a564688e228b77d16970d95885f");

        setContentView(R.layout.activity_login);
        //初始化视图控件
        etusername = (EditText) findViewById(R.id.login_username);
        etuserpwd = (EditText) findViewById(R.id.login_userpwd);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<UserModel> query = new BmobQuery<UserModel>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("username",etusername.getText().toString().trim());
                query.addWhereEqualTo("password",etuserpwd.getText().toString().trim());
                 //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(1);
                 //执行查询方法
                query.findObjects(new FindListener<UserModel>() {
                    @Override
                    public void done(List<UserModel> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > 0) {
                                UserModel bmobUser = object.get(0);
                                //保存登录用户的Id和姓名，类型
                                SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name", bmobUser.getUsername());
                                editor.putString("type", bmobUser.getType());
                                editor.putString("objectid", bmobUser.getObjectId());
                                editor.commit();


                                //登录成功
                                Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码!", Toast.LENGTH_LONG).show();
                            }

                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息

                        } else {
                            //登录失败
                            Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码!", Toast.LENGTH_LONG).show();
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                //点击登录按钮实现登录
//                UserModel bu2 = new BmobUser();
//
//                //用户名
//                bu2.setUsername(etusername.getText().toString().trim());
//                //密码
//                bu2.setPassword(etuserpwd.getText().toString().trim());
//                //bmob登录验证
//                bu2.login(new SaveListener<BmobUser>() {
//
//                    @Override
//                    public void done(BmobUser bmobUser, BmobException e) {
//                        if(e==null){
//                            //保存登录用户的Id和姓名，类型
//                            SharedPreferences sp = getSharedPreferences("companymanager", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sp.edit();
//                            editor.putString("name", bmobUser.getUsername());
//                            editor.putString("type", bmobUser.getType());
//                            editor.putString("objectid", bmobUser.getObjectId());
//                            editor.commit();
//
//
//                            //登录成功
//                            Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
//                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
//                        }else{
//                            //登录失败
//                            Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

            }
        });
    }
}
