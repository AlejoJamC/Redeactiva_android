package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class ObtenerMasVideos extends AsyncTask<Integer, Void, Boolean> {

    private Activity activity;
    private VideosListAdapater adapter;
    private List<VideoYoutube> videos = new ArrayList<VideoYoutube>();
    HttpResponse response;
    private ProgressBar progressBar;
    int p = 1;

    public ObtenerMasVideos(ProgressBar progressBar, Entrenamiento activity, VideosListAdapater adapter){
        this.progressBar = progressBar;
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Integer... parameters) {
        int npagina = parameters[0];
        this.p = npagina;
        return obtenerVideos(npagina);
    }
    
    private Boolean obtenerVideos(Integer pagina) {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( activity.getString( R.string.ur_servicio ) + "/containers/getcontainers/multimedia/" + String.valueOf(pagina) );
		
		Log.d( activity.getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("data");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    
			    String _url = "";
			    String _nombre = "";
			    String _img = "";

			    if(object.has("nombre")){
			    	_nombre = object.getString("nombre");
			    }
			    
			    if(object.has("imagen")){
			    	_img = object.getString("imagen");
			    }
			    
			    if(object.has("linkvideoimagen")){
			    	_url = object.getString("linkvideoimagen");
			    }
			    
			        
			    VideoYoutube v = new VideoYoutube(i, _url, 
			    		_img, _nombre);
			    
			   	videos.add(v);				  
			}		
			
			return true;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
		 
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            adapter.setData(videos);
        }
        
        if(this.p == 1){
        	activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.lv_videos).setVisibility(View.VISIBLE);
        }
        
        progressBar.setVisibility(View.INVISIBLE);
    }
}