package com.liberty.wikepro.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;

import com.liberty.wikepro.R;


/**
 * Created by LinJinFeng on 2016/12/23.
 */

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialog);
        this.setContentView(R.layout.progress_dialog);
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params=this.getWindow().getAttributes();
        int size= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100.0f,
                context.getResources().getDisplayMetrics());
        params.width=size;
        params.height=size;
        params.gravity= Gravity.CENTER;
        this.getWindow().setAttributes(params);
    }

    public static ProgressDialog getInstance(Activity activity){
        ProgressDialog dialog=new ProgressDialog(activity);
        return dialog;
    }
}
