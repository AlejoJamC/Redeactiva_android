package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class ObternerMasDeportes extends AsyncTask<Integer, Void, Boolean> {

    private Activity activity;
    private DeportesListAdapter adapter;
    private List<Deporte> deportes = new ArrayList<Deporte>();
    HttpResponse response;
    private ProgressBar progressBar;
    int p = 1;

    public ObternerMasDeportes(ProgressBar progressBar, Deportes activity, DeportesListAdapter adapter){
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
        return obtenerDeportes(npagina);
    }
    
    private Boolean obtenerDeportes(Integer pagina) {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( activity.getString( R.string.ur_servicio ) + "/containers/getcontainers/rendimiento/" + String.valueOf(pagina) );
		
		Log.d( activity.getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("data");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    
			    String _nombre = "";
			    String _desc = "";
			    String _reglamento = "";
			    String _logo = "";
			    String _pk = "";
			    String _rk = "";

			    if(object.has("deporte")){
			    	_nombre = object.getString("deporte");
			    }
			    
			    if(object.has("descripciondeldeporte")){
			    	_desc = object.getString("descripciondeldeporte");
			    }
			    
			    if(object.has("reglamento")){
			    	_reglamento = object.getString("reglamento");
			    }
			    
			    if(object.has("logo")){
			    	_logo = object.getString("logo");
			    }			    
			    
			    if(object.has("PartitionKey")){
			    	_pk = object.getString("PartitionKey");
			    }
			    
			    if(object.has("RowKey")){
			    	_rk = object.getString("RowKey");
			    }
			    
			    if( !TextUtils.isEmpty(_nombre) || !TextUtils.isEmpty(_desc) || !TextUtils.isEmpty(_logo) ){
			    	Deporte d = new Deporte(_nombre, 
				    		_desc,_reglamento, _logo, 
				    		_pk, _rk);
				    
				   	deportes.add(d);
			    }			    			    
			    			  
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
            adapter.setData(deportes);
        }
        
        if(this.p == 1){
        	activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.lydeportes).setVisibility(View.VISIBLE);
        }
        
        progressBar.setVisibility(View.INVISIBLE);
    }
}