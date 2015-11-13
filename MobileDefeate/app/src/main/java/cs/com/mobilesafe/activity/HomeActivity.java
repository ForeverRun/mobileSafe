package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cs.com.mobilesafe.R;
import cs.com.mobilesafe.utils.MD5Utils;

/**
 * Created by Tang on 2015/11/6.
 */
public class HomeActivity extends Activity {
    private GridView gridView;
    private String[] mItems = new String[]{"手机防盗","通讯卫士","软件管理","进程管理", "流量统计",
            "手机杀毒","缓存清理","高级工具","设置中心"};
    int[] mPics = new int[]{R.drawable.home_safe,
            R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize,
            R.drawable.home_tools, R.drawable.home_settings};

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPref = getSharedPreferences("config",MODE_PRIVATE);
        gridView = (GridView) findViewById(R.id.gv_home);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showPasswordDailog();
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this,CallSafeActivity2.class));
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this, AtoolsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                        break;
                }
            }
        });
        gridView.setAdapter(new HomeAdapter());
    }

    private void showPasswordDailog() {
        String savedPassword = mPref.getString("password",null);
        //判断是否设置过密码
        if(!TextUtils.isEmpty(savedPassword)){

            showPasswordInputDialog();
        }else{
            //如果没有，则弹出窗
            showPasswordSetDailog();
        }


    }

    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View v = View.inflate(this,R.layout.dialog_input_password,null);
        dialog.setView(v, 0, 0, 0, 0);
        final EditText etpassword = (EditText) v.findViewById(R.id.et_password);

        Button btnOk = (Button) v.findViewById(R.id.btn_ok);
        Button btnCancle = (Button) v.findViewById(R.id.btn_cancle);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etpassword.getText().toString();

                if(!TextUtils.isEmpty(password)){
                    String savedPassword = mPref.getString("password",null);
                    if(MD5Utils.encode(password).equals(savedPassword)){
                        Toast.makeText(HomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //跳转至手机防盗页面
                        startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                    }else{
                        Toast.makeText(HomeActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(HomeActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹窗消失
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showPasswordSetDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View v = View.inflate(this,R.layout.dialog_set_password,null);
        //将自定义布局文件设置给dialog
        //dialog.setView(v);
        //设置边框距离，保证在2.X版本界面兼容
        dialog.setView(v, 0, 0, 0, 0);

        final EditText etpassword = (EditText) v.findViewById(R.id.et_password);
        final EditText etpasswordconfirm = (EditText) v.findViewById(R.id.et_password_confirm);

        Button btnOk = (Button) v.findViewById(R.id.btn_ok);
        Button btnCancle = (Button) v.findViewById(R.id.btn_cancle);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etpassword.getText().toString();
                String passwordconfirm = etpasswordconfirm.getText().toString();

                if(!TextUtils.isEmpty(password) && !passwordconfirm.isEmpty()){
                    if(password.equals(passwordconfirm)){
                        Toast.makeText(HomeActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        mPref.edit().putString("password", MD5Utils.encode(password)).commit();
                        dialog.dismiss();

                        //跳转至手机防盗页面
                        startActivity(new Intent(HomeActivity.this,LostFindActivity.class));
                    }else{
                        Toast.makeText(HomeActivity.this, "输入密码不一致", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(HomeActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置取消弹窗消失
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class HomeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view = View.inflate(HomeActivity.this,R.layout.home_list_item,null);
            ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
            TextView tvItem = (TextView) view.findViewById(R.id.tv_item);

            tvItem.setText(mItems[position]);
            ivItem.setImageResource(mPics[position]);
            return view;
        }

    }
}
