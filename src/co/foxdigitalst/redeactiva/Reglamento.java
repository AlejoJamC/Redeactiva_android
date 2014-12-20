package co.foxdigitalst.redeactiva;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Reglamento extends Activity {

	WebView wv_reglamento;
	boolean loadingFinished = true;
	boolean redirect = false;
	String url;
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reglamento);
		
		Intent intent = getIntent();
		url = intent.getStringExtra("url");		
		
		Log.d("redeactiva", "url reglamento: "+url);
		
		wv_reglamento = (WebView) findViewById(R.id.wv_reglamento);
		
		wv_reglamento.getSettings().setJavaScriptEnabled(true);
		wv_reglamento.getSettings().setPluginState(PluginState.ON);
        
		wv_reglamento.setWebViewClient(new WebViewClient() {
				
			@Override
	        public boolean shouldOverrideUrlLoading(
	                WebView view, String url) {
				
	            return (false);
	        }
			
			@Override
			public void onPageFinished(WebView view, String url) {
			        // do your stuff here
				   wv_reglamento.setVisibility(View.VISIBLE);
		    	   findViewById(R.id.loading).setVisibility(View.GONE);
			}
		});
		
		wv_reglamento.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
		
	}

}
