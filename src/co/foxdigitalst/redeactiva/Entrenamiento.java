package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Entrenamiento extends Activity {

	List<VideoYoutube> vy;
	ArrayList<String> frases;
	VideosListAdapater listaVideos;
	ListView lv_videos;
	TextView frase, reto;

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
		
		frase = (TextView) findViewById(R.id.txt_fraseDia);
		
		reto = (TextView) findViewById(R.id.txtReto);
				
		new ObtenerFrases().execute();
		
	}

	private void obtenerFrases() {
		frases = new ArrayList<String>();
				
		httpHandler handler = new httpHandler();			
		String r = handler.get( getString( R.string.ur_servicio ) + "/frases");
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("frases");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 				    				    
			   	frases.add(object.getString("frase"));			  
			}				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class ObtenerFrases extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				obtenerFrases();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				frase.setText(frases.get(0));
				reto.setText(frases.get(1));        
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
	        
		}
		
	}
}
