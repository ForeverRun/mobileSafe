package cs.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import cs.com.mobilesafe.R;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup1Activity extends BaseSetupActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);

    }
    public void showNextPage() {
        startActivity(new Intent(this,Setup2Activity.class));
        finish();

        //两个界面切换的动画
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {

    }

}
