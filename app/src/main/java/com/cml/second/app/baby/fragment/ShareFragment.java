package com.cml.second.app.baby.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.cml.second.app.baby.R;
import com.cml.second.app.baby.activity.ContainerActivity;
import com.cml.second.app.common.widget.WheelView;
import com.cml.second.app.common.widget.dialog.WheelViewDialog;

import java.util.Arrays;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public class ShareFragment extends BaseFragment {

    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};

    @Bind(R.id.wheel_view)
    WheelView wheelView;

    @Bind(R.id.top_layout)
    View topLayoutView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wheelView.setOffset(1);
        wheelView.setItems(Arrays.asList(PLANETS));
        wheelView.setSperatorStartRatio(0.2f);
        wheelView.setSperatorEndRatio(0.8f);
        wheelView.setSperatorColor(Color.RED);
        wheelView.setTextColor(Color.BLUE);
        wheelView.setSelectTextColor(Color.CYAN);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Toast.makeText(getContext(), "selectedIndex: " + selectedIndex + ", item: " + item, Toast.LENGTH_LONG).show();
            }
        });
//        MenuActivity activity = (MenuActivity) getActivity();
//        LinearLayout container = activity.getToolbarCustomLayout();
//        container.removeAllViews();
    }

    @OnClick(R.id.datepicker)
    public void onDatePickerClicked(View v) {
        TimePickerView timePickerView = new TimePickerView(getContext(), TimePickerView.Type.YEAR_MONTH_DAY);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Toast.makeText(getContext(),"日期选择"+date,Toast.LENGTH_LONG).show();
            }
        });
        timePickerView.setTitle("日期选择");
        timePickerView.setRange(1900,2015);
        timePickerView.setCancelable(false);
        timePickerView.setCyclic(false);
        timePickerView.show();
    }

    @OnClick(R.id.popup_window)
    public void onPopupClicked(View v) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.view_popup_window, null);
        PopupWindow window = new PopupWindow(getActivity());
        window.setContentView(contentView);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ContainerActivity activity = (ContainerActivity) getActivity();
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
//        window.showAtLocation(activity.toolbar,Gravity.CENTER|Gravity.TOP,0,0);
        window.showAsDropDown(activity.toolbar, 0, 0);
    }

    @OnClick(R.id.wheel_dialog)
    public void onWheelDialogClicked(View v) {

        WheelViewDialog dialog = new WheelViewDialog(getActivity(), new String[]{"男", "女"});
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setOnWheelSelectListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Toast.makeText(getContext(), "onWheelDialogClicked" + item, Toast.LENGTH_LONG).show();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_share;
    }
}
