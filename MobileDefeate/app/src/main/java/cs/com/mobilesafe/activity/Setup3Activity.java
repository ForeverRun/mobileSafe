package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs.com.mobilesafe.R;

/**
 * 设置第一个页面
 * Created by Tang on 2015/11/8.
 */
public class Setup3Activity extends BaseSetupActivity {

    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        et_phone = (EditText) findViewById(R.id.et_phone);

        String phones = mPref.getString("safe_phone","");
        et_phone.setText(phones);
    }
    public void showNextPage(){
        String phones = et_phone.getText().toString().trim();//过滤空格;
        if(TextUtils.isEmpty(phones)){
            Toast.makeText(this, "安全号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
            mPref.edit().putString("safe_phone",phones).commit();

            startActivity(new Intent(this,Setup4Activity.class));
            finish();
            overridePendingTransition(R.anim.tran_in, R.anim.tran_out);


    }
    public void showPreviousPage(){
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

    public void selectcontact(View v){
        Intent intent = new Intent(this,ContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            String phonenum = data.getStringExtra("phone");
            phonenum = phonenum.replace("-"," ").replace(" ","");
            //把电话号码设置给输入框
            et_phone.setText(phonenum);
        }

    }
}
