package cs.com.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 设置滑动页面样式作为它们的父类，不需要再清单文件注册
 * Created by Tang on 2015/11/9.
 */
public abstract class BaseSetupActivity extends Activity {
    private GestureDetector mDector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            /**
             *监听手势滑动事件
             */
            public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
                //滑到右边的那一页
                if(e1.getRawX() - e2.getRawX() > 200){
                    showNextPage();
                }
                //滑到左边的那一页
                if(e2.getRawX() - e1.getRawX() > 200){
                    showPreviousPage();
                }
                return  super.onFling(e1, e2,velocityX ,velocityY );
            }
        });
    }
    /**
     * 展示下一页
     */
        public abstract void showNextPage();

        /**
         * 展示上一页
         */
        public abstract void showPreviousPage();
    /**
     * 点击下一页按钮
     *
     * @param v
     */
    public void next(View v) {
        showNextPage();
    }

    /**
     * 点击上一页按钮
     *
     * @param v
     */
    public void previous(View v) {
        showPreviousPage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //委托手势识别器处理触摸事件
        mDector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
