package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Frases extends Activity {

	ArrayList<String> frases;
	TextView frase, titulo;
	int frase_id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frases);
		
		frase = (TextView) findViewById(R.id.txt_frase);
		titulo = (TextView) findViewById(R.id.textTitulo);
		
		Intent intent = getIntent();
		frase_id = intent.getIntExtra("FRASE", 0);
		
		String _titulo = "";
		
		if(frase_id == 0){
			_titulo = getString(R.string.str_frase_d);
		}else{
			_titulo = getString(R.string.str_reto);
		}
		
		titulo.setText(_titulo);
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
				if( TextUtils.isEmpty( frases.get(frase_id) ) || TextUtils.getTrimmedLength(frases.get(frase_id)) <= 0){
					frase.setText("Lo sentimos no hay frase para Hoy");
				}else{
					frase.setText(frases.get(frase_id));
				}
								        
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			frase.setVisibility(View.VISIBLE);
			titulo.setVisibility(View.VISIBLE);
	        
		}
		
	}
}
