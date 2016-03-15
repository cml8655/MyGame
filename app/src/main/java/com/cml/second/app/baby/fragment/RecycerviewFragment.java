package com.cml.second.app.baby.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cml.second.app.baby.R;
import com.cml.second.app.common.widget.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by cmlBeliever on 2016/3/9.
 */
public class RecycerviewFragment extends BaseFragment {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * LinearLayoutManager 现行管理器，支持横向、纵向。
         GridLayoutManager 网格布局管理器
         StaggeredGridLayoutManager 瀑布就式布局管理器
         */
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));//只支持 LinearLayoutManager
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));//GridLayoutManager 专用
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new HeaderGridLayoutManager(HeaderGridLayoutManager.VERTICAL, 1));
//        recyclerView.setLayoutManager(new FixedGridLayoutManager());
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //
//        List<Integer> data;   data = new ArrayList<Integer>();
//        for (int i = 0; i < 133; i++) {
//            data.add(i);
//        }

        List<DataModel> data = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<String> childNodes = new ArrayList<>();
            int childSize = (int) (Math.random() * 10 + 5);
            for (int j = 0; j < childSize; j++) {
                childNodes.add("child:" + i + "-" + j);
            }
            DataModel model = new DataModel("title" + i, childNodes);
            data.add(new DataModel("title" + i, null));
            data.add(model);
        }

        recyclerView.setAdapter(new MyAdapter(data));
//        new ItemTouchHelper(new DragableCallback(data)).attachToRecyclerView(recyclerView);//添加拖动功能

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                if (position % 3 == 0) {
//                    textView.setText("我是header:" + data.get(position));
//                }
//            }
//        });

    }


    @Override
    protected int getContainerRes() {
        return R.layout.fragment_recyclerview;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        List<DataModel> items;

        public MyAdapter(List<DataModel> items) {
            this.items = items;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = null;
            if (viewType == 0) {
                holder = new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.adapter_recycler_header, parent, false), viewType);
            } else {
                holder = new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.adapter_recycler_items, parent, false), viewType);
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            DataModel model = items.get(position);

            if (getItemViewType(position) == 0) {
                holder.textView.setText(model.nodeName);
            } else {

                List<Map<String, String>> childData = new ArrayList<>();

                for (String str : model.childNodes) {
                    Map<String, String> map = new HashMap<>();
                    map.put("text", str);
                    childData.add(map);
                }
                holder.gridView.setAdapter(new SimpleAdapter(getContext(), childData, android.R.layout.simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1}));
            }

//            if (position % 3 == 0) {
//                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
//            } else {
//                holder.itemView.setLayoutParams(holder.params);
//            }
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).childNodes == null ? 0 : 1;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class DataModel {
        public String nodeName;
        public List<String> childNodes;

        public DataModel(String nodeName, List<String> childNodes) {
            this.nodeName = nodeName;
            this.childNodes = childNodes;
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public int viewType;

        public GridView gridView;

        public MyHolder(android.view.View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == 0) {
                textView = (TextView) itemView.findViewById(R.id.header);
            } else {
                gridView = (GridView) itemView.findViewById(R.id.grid);
            }
        }
    }
}
