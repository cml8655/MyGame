package com.cml.second.app.baby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cml.second.app.baby.R;
import com.cml.second.app.baby.fragment.BaseFragment;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public class ContainerActivity extends BaseSwipeBackActivity {

    private static final String EXTRA_CONTAINER = "ContainerActivity.EXTRA_CONTAINER";
    private static final String EXTRA_ARGS = "ContainerActivity.EXTRA_ARGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupCustomNavigation();

        //显示参数上的Fragment
        String targetClassName = getIntent().getStringExtra(EXTRA_CONTAINER);
        if (null != targetClassName) {
            BaseFragment fragment = (BaseFragment) Fragment.instantiate(this, targetClassName);
            fragment.setArguments(getIntent().getBundleExtra(EXTRA_ARGS));
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment).commit();
            setCustomTitle(fragment.getTitle());
        }

    }

    public static final void startActivity(Activity activity, Class<? extends BaseFragment> target) {
        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra(EXTRA_CONTAINER, target.getName());
        activity.startActivity(intent);
    }

    public static final void startActivity(Activity activity, Class<? extends BaseFragment> target, Bundle extraArgs) {
        Intent intent = new Intent(activity, ContainerActivity.class);
        intent.putExtra(EXTRA_CONTAINER, target.getName());
        intent.putExtra(EXTRA_ARGS, extraArgs);
        activity.startActivity(intent);
    }

}
