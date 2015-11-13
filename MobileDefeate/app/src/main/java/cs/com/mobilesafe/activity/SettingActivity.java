package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import cs.com.mobilesafe.R;
import cs.com.mobilesafe.Service.AddressService;
import cs.com.mobilesafe.utils.ServiceStatusUtils;
import cs.com.mobilesafe.view.SettingClickView;
import cs.com.mobilesafe.view.SettingItemView;

/**
 * Created by Tang on 2015/11/7.
 */
public class SettingActivity extends Activity{

    private SettingItemView sivUpdate;
    private SharedPreferences mPref;
    private SettingItemView sivAddress;
    private SettingClickView scv;
    private SettingClickView scvAddressLocation;
    private SettingItemView sivRocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mPref = getSharedPreferences("config",MODE_PRIVATE);

        initUpdateView();
        initAddressView();
        initAddressStyle();
        initAddresssLocation();
        //initRocket();
    }

    /**
     * 初始化火箭开关
     */
//    private void initRocket() {
//        sivRocket = (SettingItemView) findViewById(R.id.siv_rocket);
//        boolean rocketOpen = mPref.getBoolean("rocket_open",true);
//
//        if( rocketOpen){
//            sivRocket.setCheck(true);
//            startService(new Intent(this, RocketService.class));
//            finish();
//        }else{
//            sivRocket.setCheck(false);
//            stopService(new Intent(this, RocketService.class));
//            finish();
//        }
//
//        sivRocket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if ( sivRocket.isChecked()) {
//                    sivRocket.setCheck(false);
//                    //更新sp
//                    mPref.edit().putBoolean("rocket_open", false).commit();
//                    stopService(new Intent(SettingActivity.this, RocketService.class));
//                    finish();
//                } else {
//                    sivRocket.setCheck(true);
//                    mPref.edit().putBoolean("rocket_open", true).commit();
//                    startService(new Intent(SettingActivity.this, RocketService.class));
//                    finish();
//                }
//            }
//        });
//    }

    /**
     * 初始化自动更新的开关
     */
    private void initUpdateView() {
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

                if (sivUpdate.isChecked()) {
                    sivUpdate.setCheck(false);
                    // sivUpdate.setDesc("自动更新已关闭");
                    //更新sp
                    mPref.edit().putBoolean("auto_update", false).commit();
                } else {
                    sivUpdate.setCheck(true);
                    // sivUpdate.setDesc("自动更新已开启");
                    mPref.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }

    public void initAddressView(){

        sivAddress = (SettingItemView) findViewById(R.id.siv_address);
        boolean serviceRunning = ServiceStatusUtils.isRunning(this, "cs.com.mobilesafe.Service.AddressService");
        //根据服务是否运行，来确定用不用勾选选择框
        if(serviceRunning){
            sivAddress.setCheck(true);
        }else {
            sivAddress.setCheck(false);
        }

        sivAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sivAddress.isChecked()){
                    sivAddress.setCheck(false);
                    //关闭归属地服务
                    stopService(new Intent(SettingActivity.this, AddressActivity.class));
                }else{
                    sivAddress.setCheck(true);
                    //开启归属地服务
                    startService(new Intent(SettingActivity.this, AddressService.class));
                }
            }
        });
    }
    final String[] colors = new String[]{"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
    /**
     * 初始化显示框风格
     */
    public void initAddressStyle(){
        scv = (SettingClickView) findViewById(R.id.siv_address_style);
        scv.setTitle("归属地提示框风格");

        //读取保存的style
        int style = mPref.getInt("address_style",0);
        scv.setDesc(colors[style]);

        scv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleDialog();
            }
        });
    }

    /**
     * 弹出选择风格的提示框
     */
    private void showSingleDialog() {
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.dialog_logo);
        builder.setTitle("归属地提示框风格");
        //读取保存的style
        int style = mPref.getInt("address_style",0);

        builder.setSingleChoiceItems(colors, style, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //保存显示风格
                mPref.edit().putInt("address_style",which).commit();
                //让弹框消失
                dialog.dismiss();
                //更新组合控件描述信息
                scv.setDesc(colors[which]);
            }
        });

        builder.setNegativeButton("取消", null);
        //显示出来
        builder.show();
    }
    /**
     * 修改归属地框显示位置
     */
    public void initAddresssLocation(){
        scvAddressLocation = (SettingClickView) findViewById(R.id.siv_address_location);
        scvAddressLocation.setTitle("归属地显示位置");
        scvAddressLocation.setDesc("设置归属地显示框的提示位置");

        scvAddressLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,DragViewActivity.class));
            }
        });
    }
}
