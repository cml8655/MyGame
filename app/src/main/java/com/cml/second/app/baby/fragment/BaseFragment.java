package com.cml.second.app.baby.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cml.second.app.baby.R;

import butterknife.ButterKnife;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContainerRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getContainerRes();

    public int getTitle() {
        return R.string.title_exchange;
    }

}
