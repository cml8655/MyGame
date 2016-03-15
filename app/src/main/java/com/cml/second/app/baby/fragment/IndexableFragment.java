package com.cml.second.app.baby.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.cml.second.app.baby.R;
import com.cml.second.app.baby.fragment.util.StringMatcher;
import com.cml.second.app.common.widget.indexable.IndexableBar;
import com.cml.second.app.common.widget.indexable.IndexableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by cmlBeliever on 2016/3/8.
 */
public class IndexableFragment extends BaseFragment {

    @Bind(R.id.listview)
    IndexableListView listView;

    @Bind(R.id.bar)
    IndexableBar bar;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ArrayList<String> mItems = new ArrayList<String>();
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        Collections.sort(mItems);

        ContentAdapter adapter = new ContentAdapter(getActivity(),
                android.R.layout.simple_list_item_1, mItems);

        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);

        bar.setTextColor(Color.WHITE);
        bar.setSelectedTextColor(Color.BLUE);
        bar.setTextSize(16);

        bar.setSection(Arrays.asList("#,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",")));
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_indexable;
    }

    private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {

        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public ContentAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public int getPositionForSection(int section) {
            // If there is no item for current section, previous section will be selected
            for (int i = section; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
                                return j;
                        }
                    } else {
                        if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
                            return j;
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }

        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }
    }

    @Override
    public int getTitle() {
        return R.string.title_indexable;
    }
}
