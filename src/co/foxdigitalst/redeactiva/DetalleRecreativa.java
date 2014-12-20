package co.foxdigitalst.redeactiva;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleRecreativa extends Activity {

	String url = "/recreacion/listar";
	String id;
	ImageView logo;
	TextView txt_titulo, txt_descripcion;
	
	Proyecto proyecto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_recreativa);
		
		Intent intent = getIntent();
		id = intent.getStringExtra("id_deporte");		
				
		txt_titulo = (TextView) findViewById(R.id.txt_titulo);
		txt_descripcion = (TextView) findViewById(R.id.txt_descripcion);
		logo = (ImageView) findViewById(R.id.img_logo);
		
		if(!TextUtils.isEmpty(id)){
			 for (String retval: id.split(":")){
		         url += "/" + retval;
		      }

			new ObtenerDeporte().execute();
		}
		
		findViewById(R.id.btn_galerias).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(DetalleRecreativa.this, GaleriasRecreacion.class);
				in1.putExtra("galeria1", proyecto.getGaleria1());
				in1.putExtra("galeria2", proyecto.getGaleria2());
				in1.putExtra("galeria3", proyecto.getGaleria3());
				in1.putExtra("galeria4", proyecto.getGaleria4());
				in1.putExtra("galeria5", proyecto.getGaleria5());
				startActivity(in1);
				
			}
		});
		
		findViewById(R.id.btn_reglamento).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(DetalleRecreativa.this, ReglamentoRecreativa.class);
				in1.putExtra("url", proyecto.getSitio());
				startActivity(in1);
			}
		});
		
		
	}
	
	private void obtenerDeporte() {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( getString( R.string.ur_servicio ) + url );
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			JSONArray valores = json.getJSONArray("data");
			
			for (int i = 0; i < valores.length(); i++) {
			    JSONObject object = valores.getJSONObject(i); 
			    
			    String _nombre = "";
			    String _desc = "";
			    String _sitio = "";
			    String _logo = "";
			    String _pk = "";
			    String _rk = "";
			    String _tipo = "";

			    if(object.has("nombreprograma")){
			    	_nombre = object.getString("nombreprograma");
			    }
			    
			    if(object.has("descripcionprograma")){
			    	_desc = object.getString("descripcionprograma");
			    }
			    
			    if(object.has("paginaweb")){
			    	_sitio = object.getString("paginaweb");
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
			    			    
			    proyecto = new Proyecto(_nombre, 
			    		_desc, _tipo, _logo, 
			    		_pk, _rk, _sitio);
			    			   
			   if(object.has("galeria1")){
				   proyecto.setGaleria1(object.getString("galeria1") );
			    }
			    
			    if(object.has("galeria2")){
			    	proyecto.setGaleria2(object.getString("galeria2"));
			    }
			    
			    if(object.has("galeria3")){
			    	proyecto.setGaleria3(object.getString("galeria3"));
			    }			    
			    
			    if(object.has("galeria4")){
			    	proyecto.setGaleria4(object.getString("galeria4"));
			    }
			    
			    if(object.has("galeria5")){
			    	proyecto.setGaleria5(object.getString("galeria5"));
			    }
			    		  
			}				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class ObtenerDeporte extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				obtenerDeporte();
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
				txt_titulo.setText(proyecto.getNombre());
				txt_descripcion.setText(proyecto.getDescripcion());
				new downloadImg().execute(proyecto.getLogo());
			} catch (Exception e) {
				// TODO: handle exception
			}
				        
		}
		
	}
	
	class downloadImg extends AsyncTask<String, Void, Bitmap>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(params[0])){
				return null;
			}
						
			try {
				String urlStr = params[0].replace("\\", "");
				Log.d("Redeactiva", "url: " + urlStr);
		        java.net.URL url = new java.net.URL(urlStr);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap img = BitmapFactory.decodeStream(input);		        
		        
		        return img;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
        	
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null){
				logo.setImageBitmap(result);
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.scrv).setVisibility(View.VISIBLE);
			 
		}
		
	} 

}
