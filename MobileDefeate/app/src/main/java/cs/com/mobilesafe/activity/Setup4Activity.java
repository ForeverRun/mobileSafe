package cs.com.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import cs.com.mobilesafe.R;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup4Activity extends BaseSetupActivity {

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        mPref = getSharedPreferences("config",MODE_PRIVATE);
    }
    public void showNextPage(){
        startActivity(new Intent(this,LostFindActivity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);

        mPref.edit().putBoolean("configed",true).commit();
    }
    public void showPreviousPage(){
        startActivity(new Intent(this,Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }
}
