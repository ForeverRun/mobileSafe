package cs.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import cs.com.mobilesafe.R;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup3Activity extends BaseSetupActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }
    public void showNextPage(){
       startActivity(new Intent(this,Setup4Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
    public void showPreviousPage(){
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }
}
