package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import cs.com.mobilesafe.R;

/**
 * 手机防盗页面
 * Created by Tang on 2015/11/8.
 */
public class LostFindActivity extends Activity{

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         mPrefs = getSharedPreferences("config", MODE_PRIVATE);

         Boolean configed = mPrefs.getBoolean("configed", false);
        if(configed){
            setContentView(R.layout.activity_lost_find);
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
