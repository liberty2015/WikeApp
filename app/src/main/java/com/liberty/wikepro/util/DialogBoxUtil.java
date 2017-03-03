package com.liberty.wikepro.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liberty.wikepro.R;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class DialogBoxUtil {
    public static void showMessage(String msg, Context context){
        AlertDialog dialog=new AlertDialog.Builder(context)
                .setTitle(R.string.info)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    public static void showGetPicDialog(Context context, final LinearListCreator.OnClickCallback clickCallback){
        final Dialog dialog=new Dialog(context);
        ViewGroup contentView= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.empty_layout,null);
        ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(contentView,params1);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics()));
        new LinearListCreator.Builder(context,R.layout.txt_item)
                .needLine(true)
                .withBaseView(contentView)
                .forViews(2,params)
                .inflater(new LinearListCreator.OnInflaterCallback() {
                    @Override
                    public void onInflater(int position, View v) {
                        switch (position){
                            case 0:{
                                ((TextView)v.findViewById(R.id.txt)).setText("拍照");
                            }
                            break;
                            case 1:{
                                ((TextView)v.findViewById(R.id.txt)).setText("从相册中选择");
                            }
                            break;
                        }
                    }
                })
                .withClickCallback(new LinearListCreator.OnClickCallback() {
                    @Override
                    public void onClick(int position, View v) {
                        clickCallback.onClick(position, v);
                        dialog.dismiss();
                    }
                })
                .build()
                .create();
        dialog.show();
    }
}
