package com.grp17.dalassist.MyAccount.Staff;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grp17.dalassist.ModelClass.Login;
import com.grp17.dalassist.MyAccount.Student.AcadamicCalander.AcadamicCalanderAdapter;
import com.grp17.dalassist.R;

import java.util.List;

public class CourseInformationTabView extends Fragment implements View.OnClickListener {
    RecyclerView rvcourseInfo;
    private Context context;
    private View rootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AcadamicCalanderAdapter adapter;
    private List<Login> list, DataList;

    WebView mWebView;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView = inflater.inflate(R.layout.activity_web_view, container, false);
        init();

        return rootView;
    }

    private void init() {
        mWebView = rootView.findViewById(R.id.wb);
        mWebView.loadUrl("https://dal.brightspace.com/d2l/login");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setBuiltInZoomControls(true);//zooming button
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);//zooming


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView wView, String url) {
                if (url.indexOf("some part of my redirect uri") > -1) {
                    return true;
                } else
                    return false;
            }

        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;

                }
                return false;
            }

        });



    }

    private void initDatabse() {
    }

    private void initRecycler() {
        mRecyclerView = rootView.findViewById(R.id.rvcourseInfo);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onClick(View v) {

    }
}
