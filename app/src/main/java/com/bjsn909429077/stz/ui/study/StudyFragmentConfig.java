package com.bjsn909429077.stz.ui.study;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bjsn909429077.stz.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class StudyFragmentConfig {

    public static void bigOrSmall(ArrayList<String> strings, TabLayout tablayout, Context mContext) {
        for (int x = 0; x < strings.size(); x++) {
            TabLayout.Tab tab = tablayout.getTabAt(x);
            View inflate = View.inflate(mContext, R.layout.tab_bg, null);
            TextView textView = inflate.findViewById(R.id.choose_icon_tab_tv);
            textView.setText(strings.get(x));
            tab.setCustomView(inflate);
            //  tablayout.addTab(tab);
            TextPaint textViewPaint = textView.getPaint();
            if (x == 0) {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                //textView.setSelected(true);
                textView.setTextSize(19);
                textViewPaint.setFakeBoldText(true);
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                //textView.setSelected(false);
                textView.setTextSize(16);
                textViewPaint.setFakeBoldText(false);
            }
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_selected));
                    TextPaint paint = ((TextView) view).getPaint();
                    ((TextView) view).setTextSize(19);
                    paint.setFakeBoldText(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.home_tab_noselected));
                    TextPaint paint = ((TextView) view).getPaint();
                    paint.setFakeBoldText(false);
                    ((TextView) view).setTextSize(16);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
