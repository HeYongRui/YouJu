package com.heyongrui.user;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DialogUtil;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.user.utils.AliPay;
import com.heyongrui.user.utils.WXPay;

import java.util.List;

@Route(path = ConfigConstants.PATH_ENCOURAGE)
public class EncourageActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_encourage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);
        Button btnWxEncourage = findViewById(R.id.btn_wx_encourage);
        Button btnAliEncourage = findViewById(R.id.btn_ali_encourage);
        setBtnBackground(btnWxEncourage);
        setBtnBackground(btnAliEncourage);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.btn_wx_encourage) {
                PermissionUtils.permission(PermissionConstants.STORAGE)
                        .rationale((activity, shouldRequest) -> shouldRequest.again(true))
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                WXPay.startWeZhi(EncourageActivity.this, findViewById(R.id.iv_wx_qrcode));
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                DialogUtil.showPermissionDialog(EncourageActivity.this, getString(com.heyongrui.base.R.string.read_write_permisson));
                            }
                        }).request();
            } else if (id == R.id.btn_ali_encourage) {
                AliPay.startAlipayClient(this, "fkx00263qmoxalrjiwqgo21");
            }
        }, R.id.iv_back, R.id.btn_wx_encourage, R.id.btn_ali_encourage);
    }

    private void setBtnBackground(View view) {
        int roundRadius = 50;
        ViewCompat.setBackgroundTintList(view, null);
        GradientDrawable normalDrawable = new DrawableUtil.DrawableBuilder(this).setColor(ContextCompat.getColor(this, R.color.background)).setGradientRoundRadius(roundRadius).createGradientDrawable();
        GradientDrawable pressDrawable = new DrawableUtil.DrawableBuilder(this).setColor(ContextCompat.getColor(this, R.color.gray)).setGradientRoundRadius(roundRadius).createGradientDrawable();
        StateListDrawable stateListDrawable = new DrawableUtil.DrawableBuilder(this).setStateListNormalDrawable(normalDrawable).setStateListPressedDrawable(pressDrawable).createStateListDrawable();
        ViewCompat.setBackground(view, stateListDrawable);
    }
}
