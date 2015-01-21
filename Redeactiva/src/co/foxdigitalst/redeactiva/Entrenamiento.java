package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Entrenamiento extends Activity {

	List<VideoYoutube> vy;	
	VideosListAdapater listaVideos;
	ListView lv_videos;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrenamiento);
		
		lv_videos = (ListView) findViewById(R.id.lv_videos);
		vy = new ArrayList<VideoYoutube>();
		
		final ProgressBar progressBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyle);
        LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(progressBarParams);
        progressBar.setPadding(6, 6, 6, 6);
        progressBar.setVisibility(View.INVISIBLE);

        LinearLayout footerLinearLayout = new LinearLayout(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        footerLinearLayout.setGravity(Gravity.CENTER);
        footerLinearLayout.setLayoutParams(layoutParams);
        footerLinearLayout.addView(progressBar);
		
		lv_videos.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	new ObtenerMasVideos(progressBar,Entrenamiento.this,listaVideos).execute(page);
            }
        });
		
		lv_videos.addFooterView(footerLinearLayout);
		
		listaVideos = new VideosListAdapater(Entrenamiento.this, vy);
		 
        // setting list adapter
        lv_videos.setAdapter(listaVideos);
        
        new ObtenerMasVideos(progressBar,Entrenamiento.this,listaVideos).execute(1);
		
		lv_videos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( vy.get(position).getUrl() )));
				
			}
		}); 
			
		
		
	}
	
}
