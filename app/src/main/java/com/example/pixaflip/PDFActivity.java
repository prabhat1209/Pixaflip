package com.example.pixaflip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PDFActivity extends AppCompatActivity {

    WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f);

        webView = findViewById(R.id.WV);
        progressBar = findViewById(R.id.pb);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient());
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",0);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(PDFActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }

        });
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+DisplayPdfActivity.list.get(position).getPdfUrl());

    }
}
