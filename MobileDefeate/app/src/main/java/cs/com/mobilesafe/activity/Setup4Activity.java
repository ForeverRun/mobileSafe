package cs.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import cs.com.mobilesafe.R;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup4Activity extends BaseSetupActivity {

    private CheckBox mCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        mCheck = (CheckBox) findViewById(R.id.cb_protect);

       boolean protect = mPref.getBoolean("protect",false);
        //当check发生改变时，回掉此方法
        if(protect){
            mCheck.setText("防盗保护已经开启");
            mCheck.setChecked(true);
        }else{
            mCheck.setText("防盗保护没有开启");
            mCheck.setChecked(false);
        }
        mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mCheck.setText("防盗保护已经开启");
                    mPref.edit().putBoolean("protect",true).commit();
                }else{
                    mCheck.setText("防盗保护没有开启");
                    mPref.edit().putBoolean("protect",false).commit();
                }
            }
        });
    }

    public void showNextPage() {
        startActivity(new Intent(this, LostFindActivity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);

        mPref.edit().putBoolean("configed", true).commit();
    }

    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }
}
