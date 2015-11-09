package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import cs.com.mobilesafe.R;
import cs.com.mobilesafe.view.SettingItemView;

/**
 * Created by Tang on 2015/11/7.
 */
public class SettingActivity extends Activity{

    private SettingItemView sivUpdate;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mPref = getSharedPreferences("config",MODE_PRIVATE);

        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
       // sivUpdate.setTitle("自动更新设置");

        boolean autoupdate = mPref.getBoolean("auto_update",true);

        if(autoupdate){
         //   sivUpdate.setDesc("自动更新已开启");
            sivUpdate.setCheck(true);
        }else{
         //   sivUpdate.setDesc("自动更新已关闭");
            sivUpdate.setCheck(false);
        }


        //设置条目点击事件
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sivUpdate.isChecked()){
                    sivUpdate.setCheck(false);
                   // sivUpdate.setDesc("自动更新已关闭");
                    //更新sp
                    mPref.edit().putBoolean("auto_update",false).commit();
                }else{
                    sivUpdate.setCheck(true);
                   // sivUpdate.setDesc("自动更新已开启");
                    mPref.edit().putBoolean("auto_update",true).commit();
                }
            }
        });
    }
}
