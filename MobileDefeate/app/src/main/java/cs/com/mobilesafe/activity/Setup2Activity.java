package cs.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import cs.com.mobilesafe.R;
import cs.com.mobilesafe.view.SettingItemView;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup2Activity extends BaseSetupActivity {

    private SettingItemView sivSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        sivSim = (SettingItemView) findViewById(R.id.siv_sim);
        String sim = mPref.getString("sim", null);
        if(!TextUtils.isEmpty(sim)){
            sivSim.setCheck(true);
        }else{
            sivSim.setCheck(false);
        }
        sivSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivSim.isChecked()) {
                    sivSim.setCheck(false);
                    //设置序列号
                    mPref.edit().remove("sim").commit();
                } else {
                    sivSim.setCheck(true);
                    //保存sim卡序列号
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    //获取sim卡序列号
                    String simSerialNumber = tm.getSimSerialNumber();
                    //Log.e("震哥", simSerialNumber);
                    //将sim卡信息保存在sp中
                    mPref.edit().putString("sim", simSerialNumber).commit();
                }

            }
        });

    }

    public void showNextPage() {
        // 如果sim卡没有绑定,就不允许进入下一个页面
        String sim = mPref.getString("sim", null);
        if (TextUtils.isEmpty(sim)) {
            Toast.makeText(this, "必须绑定sim卡!",Toast.LENGTH_SHORT ).show();
            return;
        }
        startActivity(new Intent(this, Setup3Activity.class));
        finish();

        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    /**
     * 展示上一页
     */
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

}

