package com.semihbg.gorun.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.semihbg.gorun.R;
import com.semihbg.gorun.core.AppConstants;

public class DocActivity extends AppCompatActivity {

    private WebView docWebView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);

        //MenuBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Docs");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Components
        docWebView = findViewById(R.id.docWebView);

        //DocWebView config
        docWebView.setWebViewClient(new GoDocPackageFilterWebViewClient());
        docWebView.setWebChromeClient(new WebChromeClient());
        docWebView.getSettings().setLoadWithOverviewMode(true);
        docWebView.getSettings().setUseWideViewPort(true);
        docWebView.getSettings().setJavaScriptEnabled(true);
        docWebView.getSettings().setLoadsImagesAutomatically(true);

        docWebView.loadUrl(AppConstants.GO_DOC_URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingItem) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void displayContinueDialog(Uri uri) {
        new AlertDialog.Builder(this)
                .setTitle("Continue")
                .setMessage("This page is only for package docs, You can continue on browser\n Url : " + uri.toString())
                .setIcon(R.drawable.go_logo_blue)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> redirectToChrome(uri))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void redirectToChrome(Uri uri) {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(uri);
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
        }
    }

    private class GoDocPackageFilterWebViewClient extends WebViewClient {

        private static final String SUPPORTED_URL_PREFIX = "https://golang.org/pkg/";

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().startsWith(SUPPORTED_URL_PREFIX)) return false;
            else DocActivity.this.displayContinueDialog(request.getUrl());
            return true;
        }

    }

}


