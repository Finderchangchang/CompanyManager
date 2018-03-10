package gy.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import gy.companymanager.adapter.CommonAdapter;
import gy.companymanager.adapter.CommonViewHolder;
import gy.companymanager.model.TaskModel;
import gy.companymanager.model.UserModel;

//任务列表
public class TaskListActivty extends Activity {

    private ListView task_lv;//任务列表
    private EditText task_et_searchname;//任务名称查询框
    private EditText task_et_searchtype;//任务类型
    private String[] strType;
    private List<TaskModel>listtask;//任务集合
    private CommonAdapter<TaskModel> commonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_activty);
        //初始化界面视图控件
        task_lv= (ListView) findViewById(R.id.task_lv);

        strType=getResources().getStringArray(R.array.tasktype);
        task_et_searchname= (EditText) findViewById(R.id.task_et_searchname);
        task_et_searchtype= (EditText) findViewById(R.id.task_et_searchtype);
        //设置不可编辑，但是响应点击事件
        task_et_searchtype.setFocusable(false);
        task_et_searchtype.addTextChangedListener(watcher);
        task_et_searchtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择任务类型
                new AlertDialog.Builder(TaskListActivty.this)
                        .setTitle("选择员工类型")
                        .setItems(strType, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //选择以后赋值给编辑框
                                task_et_searchtype.setText(strType[which]);
                            }
                        }).show();
            }
        });
        commonAdapter=new CommonAdapter<TaskModel>(TaskListActivty.this,listtask,R.layout.item_task) {
            @Override
            public void convert(CommonViewHolder holder, TaskModel taskModel, int position) {
                holder.setText(R.id.task_item_name,taskModel.getTitle());
                holder.setText(R.id.task_item_type,taskModel.getState());
                holder.setText(R.id.task_item_content,taskModel.getContent());
                holder.setText(R.id.task_item_start,taskModel.getTimestart());
                holder.setText(R.id.task_item_end,taskModel.getTimeend());
            }
        };
task_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到详细信息
        Intent intent=new Intent(TaskListActivty.this,AddTaskActivity.class);
        intent.putExtra("objectid",listtask.get(position).getObjectId());
        startActivity(intent);
    }
});
    }
    private void Search(String name) {
        BmobQuery<TaskModel> query = new BmobQuery<TaskModel>();
        if(!name.equals(""))
        {
            query.addWhereEqualTo("username", name);
        }
        query.addWhereEqualTo("type",task_et_searchtype.getText().toString().trim());

        query.findObjects(new FindListener<TaskModel>() {
            @Override
            public void done(List<TaskModel> object, BmobException e) {
                if (e == null) {
                    //查询成功以后刷新数据
                    listtask = object;
                    commonAdapter.refresh(listtask);
                    //toast("查询用户成功:"+object.size());
                } else {
                    //查询失败
                    Toast.makeText(TaskListActivty.this, "查询信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Search(task_et_searchname.getText().toString().trim());
        }
    };
}
