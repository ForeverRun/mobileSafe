package cs.com.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cs.com.mobilesafe.R;

/**
 * 设置中心的自定义组合控件
 * Created by Tang on 2015/11/7.
 */
public class SettingItemView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;
    private String mTitle;
    private String mDescOff;
    private String mDescOn;

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //根据属性得到值
        mTitle = attrs.getAttributeValue(NAMESPACE,"mytitle");
       // Log.e("震哥",mTitle);
        mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");

        initView();

 //       int attributeCount = attrs.getAttributeCount();
//        for (int i = 0; i < attributeCount;i++){
//            String attributeName = attrs.getAttributeName(i);
//            String attributeValue = attrs.getAttributeValue(i);
//            Log.e("震哥",attributeName+"="+attributeValue);
//        }

    }
    public SettingItemView(Context context) {
        super(context);
        initView();
    }
    /**
     * 初始化布局
     */
    private void initView() {
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);

        setTitle(mTitle);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }

    public boolean isChecked(){
        return  cbStatus.isChecked();
    }

    public void setCheck(boolean check){
        cbStatus.setChecked(check);
        //根据选择的状态，更新文本描述
        if(check){
            setDesc(mDescOn);
        }else{
            setDesc(mDescOff);
        }
    }
}
