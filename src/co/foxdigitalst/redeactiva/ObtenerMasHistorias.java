package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class ObtenerMasHistorias extends AsyncTask<Integer, Void, Boolean> {

    private View activity;
    private DeportistasListAdapter adapter;
    private List<Historia> historias = new ArrayList<Historia>();
    HttpResponse response;
    private ProgressBar progressBar;
    int p = 1;

    public ObtenerMasHistorias(ProgressBar progressBar, View rootView, DeportistasListAdapter adapter){
        this.progressBar = progressBar;
        this.activity = rootView;
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
        return obtenerEHistorias(npagina);
    }
    
    private Boolean obtenerEHistorias(Integer pagina) {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( activity.getContext().getString( R.string.ur_servicio ) + "/historias/" + String.valueOf(pagina) );
		
		Log.d( activity.getContext().getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("data");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    Historia historia = new Historia(object.getString("objectId"), object.getString("descripcion"), 
			    		object.getString("adjunto"), object.getString("iduser"));
			    
			    historias.add(historia);				  
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
            adapter.setData(historias);
        }
        
        if(this.p == 1){
        	activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.lv_historias).setVisibility(View.VISIBLE);
        }
        
        progressBar.setVisibility(View.INVISIBLE);
    }
}
