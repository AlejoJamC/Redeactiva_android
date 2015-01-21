package co.foxdigitalst.redeactiva;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DetalleDeporte extends Activity {
	
	String url = "/deportes/listar";
	String id;
	ImageView logo;
	TextView txt_titulo, txt_descripcion;
	
	Deporte deporte;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_deporte);
		
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
				Intent in1 = new Intent(DetalleDeporte.this, Galerias.class);
				in1.putExtra("galeria1", deporte.getGaleria1());
				in1.putExtra("galeria2", deporte.getGaleria2());
				in1.putExtra("galeria3", deporte.getGaleria3());
				in1.putExtra("galeria4", deporte.getGaleria4());
				in1.putExtra("galeria5", deporte.getGaleria5());
				startActivity(in1);
			}
		});
		
		findViewById(R.id.btn_reglamento).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				String url = deporte.getReglamento();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
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
			    			    
			   deporte = new Deporte(_nombre, 
			    		_desc,_reglamento, _logo, 
			    		_pk, _rk);		
			   
			   if(object.has("galeria1")){
			    	deporte.setGaleria1(object.getString("galeria1") );
			    }
			    
			    if(object.has("galeria2")){
			    	deporte.setGaleria2(object.getString("galeria2"));
			    }
			    
			    if(object.has("galeria3")){
			    	deporte.setGaleria3(object.getString("galeria3"));
			    }			    
			    
			    if(object.has("galeria4")){
			    	deporte.setGaleria4(object.getString("galeria4"));
			    }
			    
			    if(object.has("galeria5")){
			    	deporte.setGaleria5(object.getString("galeria5"));
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
				txt_titulo.setText(deporte.getNombre());
				txt_descripcion.setText(deporte.getDescripcion());
				new downloadImg().execute(deporte.getLogo());
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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
