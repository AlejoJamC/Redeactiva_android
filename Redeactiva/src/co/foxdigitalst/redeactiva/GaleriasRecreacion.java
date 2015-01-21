package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class GaleriasRecreacion extends Activity {

	String url = "/recreacion/galerias/";
	ArrayList<Galeria> galerias;
	GaleriasAdapter listaGalerias;
	ListView lv_galerias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gelerias_recreacion);
		
		lv_galerias = (ListView) findViewById(R.id.lv_galerias);
		
		new ObtenerGalerias().execute();
	}
	
	private void obtenerGalerias() {
		galerias = new ArrayList<Galeria>();
		
		Intent intent = getIntent();
		galerias.add(new Galeria(1, intent.getStringExtra("galeria1")));
		galerias.add(new Galeria(2, intent.getStringExtra("galeria2")));
		galerias.add(new Galeria(3, intent.getStringExtra("galeria3")));
		galerias.add(new Galeria(4, intent.getStringExtra("galeria4")));
		galerias.add(new Galeria(5, intent.getStringExtra("galeria5")));
		 
    }
	
	class ObtenerGalerias extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				obtenerGalerias();
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
				listaGalerias = new GaleriasAdapter(GaleriasRecreacion.this, galerias);
				 
		        // setting list adapter
		        lv_galerias.setAdapter(listaGalerias);
		        lv_galerias.setVisibility(View.VISIBLE);		        
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.lv_galerias).setVisibility(View.VISIBLE);
	        
		}
		
	}

}
