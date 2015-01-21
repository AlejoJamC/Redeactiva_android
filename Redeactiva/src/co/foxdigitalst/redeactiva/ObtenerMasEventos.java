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

public class ObtenerMasEventos extends AsyncTask<Integer, Void, Boolean> {

    private Activity activity;
    private EventosListAdapter adapter;
    private List<Evento> eventos = new ArrayList<Evento>();
    HttpResponse response;
    private ProgressBar progressBar;
    int p = 1;

    public ObtenerMasEventos(ProgressBar progressBar, Eventos activity, EventosListAdapter adapter){
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
        return obtenerEventos(npagina);
    }
    
    private Boolean obtenerEventos(Integer pagina) {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( activity.getString( R.string.ur_servicio ) + "/containers/getcontainers/calendario/" + String.valueOf(pagina) );
		
		Log.d( activity.getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("data");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    
			    String _nombre = "";
			    String _desc = "";
			    String _tipo = "";
			    String _fechaini = "";
			    String _fechafin = "";
			    String _entidad = "";
			    String _clase = "";
			    String _web = "";
			    String _email= "";

			    if(object.has("evento")){
			    	_nombre = object.getString("evento");
			    }
			    
			    if(object.has("descripciondelevento")){
			    	_desc = object.getString("descripciondelevento");
			    }
			    
			    if(object.has("tipo")){
			    	_tipo = object.getString("tipo");
			    }
			    
			    if(object.has("fechainicio")){
			    	_fechaini = object.getString("fechainicio");
			    }			    
			    
			    if(object.has("fechafinal")){
			    	_fechafin = object.getString("fechafinal");
			    }
			    
			    if(object.has("entidad")){
			    	_entidad = object.getString("entidad");
			    }
			    
			    if(object.has("clasedeevento")){
			    	_clase = object.getString("clasedeevento");
			    }			    
			    
			    if(object.has("paginaweb")){
			    	_web = object.getString("paginaweb");
			    }
			    
			    if(object.has("contacto")){
			    	_email = object.getString("contacto");
			    }
			    			    
			    Evento e = new Evento(i, _nombre, _tipo, _fechaini, _fechafin, _entidad, _desc, _clase, _web, _email);
			    
			   	eventos.add(e);					  
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
            adapter.setData(eventos);
        }
        
        if(this.p == 1){
        	activity.findViewById(R.id.loading).setVisibility(View.GONE);
			activity.findViewById(R.id.lista_eventos).setVisibility(View.VISIBLE);
        }
        
        progressBar.setVisibility(View.INVISIBLE);
    }
}