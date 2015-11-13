package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cs.com.mobilesafe.R;

/**
 * 手机防盗页面
 * Created by Tang on 2015/11/8.
 */
public class LostFindActivity extends Activity{

    private SharedPreferences mPrefs;
    private TextView mSafePhone;
    private ImageView ivProtect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         mPrefs = getSharedPreferences("config", MODE_PRIVATE);

         Boolean configed = mPrefs.getBoolean("configed", false);
        if(configed){
            setContentView(R.layout.activity_lost_find);
            //根据sp更新号码
            mSafePhone = (TextView) findViewById(R.id.tv_safe_phone);
            String phone = mPrefs.getString("safe_phone","");
            mSafePhone.setText(phone);
            //更新锁子
            ivProtect = (ImageView) findViewById(R.id.iv_protect);
            boolean protect = mPrefs.getBoolean("protect",false);
            if(protect){
                ivProtect.setImageResource(R.drawable.lock);
               // mPrefs.edit().putBoolean("protect",true).commit();
            }else{
                ivProtect.setImageResource(R.drawable.unlock);
               // mPrefs.edit().putBoolean("protect",false).commit();
            }
        }else{
            //跳转到设置向导页面
            startActivity(new Intent(this,Setup1Activity.class));
            finish();
        }
    }

    /**
     * 重新进入设置向导页面
     * @param v
     */
    public void reEnter(View v){
        startActivity(new Intent(this,Setup1Activity.class));
        finish();
    }
}
