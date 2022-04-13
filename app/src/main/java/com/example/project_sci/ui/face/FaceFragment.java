package com.example.project_sci.ui.face;

import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import com.example.project_sci.R;


public class FaceFragment extends Fragment {

    WebView myWebView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_face, container, false);
        WebView myWebView = root.findViewById(R.id.webview5);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://www.facebook.com/FacultyofScienceASU");
        return root;
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        if(myWebView.canGoBack())
        {
            myWebView.goBack();
        }
        else
         super.setEnterSharedElementCallback(callback);
    }
}