package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.app.Activity;

public class Federaciones extends Activity {

	ArrayList<Federacion> federaciones;
	FederacionesListAdapter listaFederaciones;
	ListView lv_federaciones;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_federaciones);
		
		lv_federaciones = (ListView) findViewById(R.id.lv_federaciones);
		
		new ObtenerFederaciones().execute();
		
	}

	private void obtenerFederaciones() {
		federaciones = new ArrayList<Federacion>();
				
		httpHandler handler = new httpHandler();
		String r = handler.get( getString( R.string.ur_servicio ) + "/federaciones" );
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("federaciones");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    Federacion f = new Federacion(object.getInt("id"), object.getString("nombre"), 
			    		object.getString("direccion"), object.getString("telefono"));
			    
			    federaciones.add(f);			  
			}				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class ObtenerFederaciones extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				obtenerFederaciones();
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
				listaFederaciones = new FederacionesListAdapter(Federaciones.this, federaciones);
				 
		        // setting list adapter
		        lv_federaciones.setAdapter(listaFederaciones);
		        lv_federaciones.setVisibility(View.VISIBLE);		        
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.lv_federaciones).setVisibility(View.VISIBLE);
	        
		}
		
	}
	
}
