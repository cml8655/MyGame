package com.cml.second.app.baby.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cml.second.app.baby.R;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/2/25.
 */
public class DefaultSelectorDialog {
    private AlertDialog dialog;
    private OnItemClickListener listener;
    private TextView titleView;

    public DefaultSelectorDialog(final Activity activity, List<SelectorItem> items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog_Style);
        builder.setAdapter(new SelectorAdapter(activity, items), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (null != listener) {
                    listener.onClick(dialog, which);
                }
            }
        });
        titleView = new TextView(activity);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(20);
        titleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        builder.setCustomTitle(titleView);
        dialog = builder.create();
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.width = activity.getResources().getDisplayMetrics().widthPixels;
//        dialog.getWindow().setAttributes(lp);
    }

    public DefaultSelectorDialog setGravity(int gravity) {
        dialog.getWindow().setGravity(gravity);
        return this;
    }

    public DefaultSelectorDialog setTitle(String title) {
        titleView.setText(title);
        return this;
    }

    public DefaultSelectorDialog setListener(OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static interface OnItemClickListener {
        void onClick(AlertDialog dialog, int index);
    }

    public static class SelectorItem {
        public final String text;
        public final int iconRes;

        public SelectorItem(String text, int iconRes) {
            this.text = text;
            this.iconRes = iconRes;
        }
    }

    private static class SelectorAdapter extends BaseAdapter {

        private List<SelectorItem> data;
        private Context context;

        public SelectorAdapter(Context context, List<SelectorItem> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (null == view) {
                view = LayoutInflater.from(context).inflate(R.layout.adapter_selector_dialog, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.txt);
            tv.setText(data.get(position).text);
            return view;
        }
    }
}
