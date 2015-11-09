package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cs.com.mobilesafe.R;
import cs.com.mobilesafe.utils.StreamUtils;

public class SplashActivity extends Activity {


    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2 ;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;

    private TextView tvVersion;
    private TextView tvProgress;
    private RelativeLayout rlRoot;
    // 服务器返回的信息
    private String mVersionName;// 版本名
    private int mVersionCode;// 版本号
    //private float mVersionCode;
    private String mDesc;// 版本描述
    private String mDownloadUrl;// 下载地址

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch(msg.what){
               case CODE_UPDATE_DIALOG:
                   showUpdateDialog();
                   break;
               case CODE_URL_ERROR :
                   Toast.makeText(SplashActivity.this, "网络地址错误", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_NET_ERROR:
                   Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_JSON_ERROR:
                   Toast.makeText(SplashActivity.this, "数据解析异常", Toast.LENGTH_SHORT).show();
                   enterHome();
                   break;
               case CODE_ENTER_HOME:
                   enterHome();
                   break;
           }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号：" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_download);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        SharedPreferences mPref = getSharedPreferences("config",MODE_PRIVATE);
        //判断是否需要更新
       boolean autoupdate = mPref.getBoolean("auto_update",true);
        if(autoupdate){
            checkVersion();
        }else{
            //延迟3秒发消息
            handler.sendEmptyMessageDelayed(CODE_ENTER_HOME,3000);
        }
        //渐变动画特效
        AlphaAnimation anim = new AlphaAnimation(0.3f,1f);
        anim.setDuration(2000);
        rlRoot.startAnimation(anim);
    }


    /**
     * 获取版本名称
     * @return
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取app版本号
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private void checkVersion() {
       final long startTime = System.currentTimeMillis();
        Thread th = new Thread(){
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                HttpURLConnection conn = null;
                try {
                    //本机地址用localhost, 但是如果用模拟器加载本机的地址时,可以用ip(10.0.2.2)来替换
                    URL url = new URL("http://192.168.3.84:8080/my/update.json");
                    //拿到连接对象
                    conn = (HttpURLConnection) url.openConnection();
                    //初始化连接对象
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();

                    if(conn.getResponseCode() == 200){
                        InputStream is = conn.getInputStream();
                        String result = StreamUtils.readFromStream(is);
                       // Log.e("震哥","555555");
                        //获取json对象
                        JSONObject job = new JSONObject(result);
                        mVersionName = job.getString("versionName");
                        mVersionCode = job.getInt("versionCode");
//                        String code = job.getString("versionCode");
//                        float mVersionCode = Float.parseFloat(code);
//                        Log.e("震哥",""+mVersionCode);
                        mDesc = job.getString("description");
                        mDownloadUrl = job.getString("downloadUri");
                        Log.e("震哥",mDownloadUrl);
                        //System.out.println(mVersionCode + ":" + mVersionName + ":" + mDesc + ":" + mDownloadUrl);
                        if(mVersionCode > getVersionCode()){
                        //弹出对话框提示更新
                            msg.what = CODE_UPDATE_DIALOG;
                        }else{
                           msg.what = CODE_ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    long endTime = System.currentTimeMillis();
                    long usedTime = endTime - startTime;
                    if(usedTime < 3000){
                        //强制休眠2秒，保证闪屏页展示2秒                        try {
                        try {
                            Thread.sleep(3000 - usedTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(msg);
                    if(conn != null){
                        //关闭连接
                        conn.disconnect();
                    }
                }
            }
        };
        th.start();

    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.btn_default);
        builder.setTitle("发现最新版本：" + mVersionName);
        //设置不让用户点返回
        //builder.setCancelable(false);
        builder.setMessage(mDesc);

        //设置按钮
        builder.setPositiveButton("确定",new OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Log.e("震哥", "立即更新");
                downLoad();
            }
        });
        builder.setNegativeButton("下次再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }



    private void enterHome() {
        Intent it = new Intent(this,HomeActivity.class);
        startActivity(it);
        finish();
    }

    private void downLoad() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //设置进度条可见
            tvProgress.setVisibility(View.VISIBLE);

            String target = Environment.getExternalStorageDirectory()+"/22.apk";

            HttpUtils utils = new HttpUtils();
            HttpHandler<File> handle = utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    tvProgress.setText("下载进度：" + current * 100 / total + "%");
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //跳转到系统下载界面
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.addCategory(Intent.CATEGORY_DEFAULT);
                    it.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                    // startActivity(it);
                    startActivityForResult(it,0);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(SplashActivity.this, "下载失败，思密达", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(SplashActivity.this, "请检查SD卡", Toast.LENGTH_SHORT).show();
        }

    }
    //如果用户下载成功不安装的话，调用此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }
}
