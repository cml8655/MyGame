package com.cml.second.app.baby.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cml.second.app.baby.R;
import com.cml.second.app.baby.activity.MenuActivity;
import com.socks.library.KLog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public class MainFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuActivity activity = (MenuActivity) getActivity();
        LinearLayout container = activity.getToolbarCustomLayout();

        ImageView img = new ImageView(activity);
        img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Glide.with(this).load("http://pic11.nipic.com/20101107/5963073_141803043683_2.gif").asGif().placeholder(R.drawable.success_circle).into(img);
//        Glide.with(this).load("http://b.hiphotos.baidu.com/zhidao/pic/item/63d9f2d3572c11dfb068871a612762d0f703c249.jpg").into(img);
        container.addView(img);

//        <ImageView
//        android:id="@+id/img"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:fitsSystemWindows="true"
//        android:src="@mipmap/ic_launcher"/>

    }

    UMShareAPI umShareAPI;

    @OnClick(R.id.qq_login)
    public void qqLoginClicked(View v) {

        umShareAPI = UMShareAPI.get(getContext());

        umShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                KLog.d(TAG, map);
                // {access_token=8BDAD96F1EAFAA875436E006082E018D, page_type=, appid=, pfkey=e68a742c8d32ee5d7a5f4129db32742a, uid=5BAF83B564C6468D3B5FFBB64F024F82, auth_time=, sendinstall=, pf=desktop_m_qq-10000144-android-2002-, expires_in=7776000, pay_token=217DAB97919A8E11644D6F4FC84B3C5A, ret=0, openid=5BAF83B564C6468D3B5FFBB64F024F82}
                if (map.containsKey("uid")) {//授权成功，uid对为用户id
                    String qqUserId = map.get("uid");
                    Toast.makeText(getActivity(), "qq授权登录成功" + qqUserId, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(getActivity(), "onError", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_main;
    }
}
