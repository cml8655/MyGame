package com.cml.second.app.baby.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cml.second.app.baby.R;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/2/25.
 */
public class SelectorDialog extends Dialog {

    private List<SelectorItem> items;
    private String title;

    public SelectorDialog(Context context, List<SelectorItem> items) {
        this(context, items, R.style.MyDialog);
    }

    public SelectorDialog(Context context, List<SelectorItem> items, int themeResId) {
        super(context, themeResId);
        this.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_selector_dialog);
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);

        ListView container = (ListView) findViewById(R.id.listview);
        container.setAdapter(new SelectorAdapter(getContext(), items));
    }

    //
//    public void SelectorDialog(final Activity activity, List<SelectorItem> items) {
//        this.items = items;
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setAdapter(new SelectorAdapter(activity, items), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(activity, "点击" + which, Toast.LENGTH_LONG).show();
//            }
//        });
//        dialog = builder.create();
//    }

    public SelectorDialog setGravity(int gravity) {
        getWindow().setGravity(gravity);
        return this;
    }

    public SelectorDialog setTitle(String title) {
        this.title = title;
        return this;
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
