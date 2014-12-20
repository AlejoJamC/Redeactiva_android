package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Perfil extends Activity {

	Usuario usuario;
	int id;
	TextView txt_email;
	EditText pass, c_pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("id_user", 0);	
		
		txt_email = (TextView)findViewById(R.id.txt_email);
		pass = (EditText)findViewById(R.id.edt_pass);
		c_pass = (EditText)findViewById(R.id.edt_confirmar_pass);
		
		if(id>0){
			new ObtenerPerfil().execute();
		}
		
		findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean titulo_ok = validarPass();
				
				if(!titulo_ok){
					new AlertDialog.Builder(Perfil.this)
				    .setTitle("Campos Obligatorios")
				    .setMessage("Verifique que su nueva contraseña no esté vacia y confirmela. Vuelva a intentarlo..")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	dialog.cancel();
				        }
				     })				    
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
					
				}else{
					new GuardarPerfil().execute();
				}
				
			}
		});
		
		findViewById(R.id.btn_salir).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				salir();
			}
		});
		
		
	}
	
	protected void salir() {
		// TODO Auto-generated method stub
		configuracionDB admin = new configuracionDB(Perfil.this,
	            "redeactiva", null, 1);
	    SQLiteDatabase bd = admin.getWritableDatabase();        
	    int cant = bd.delete("user_redeactiva", "codigo=1", null);
	    bd.close();
	    
	    if (cant == 1)
	    	Log.d(getString(R.string.app_name), "Se borró Email satisfactoriamente");            
	    else
	    	Log.d(getString(R.string.app_name), "No existia");
		
		Intent i = new Intent(Perfil.this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(i);
	    finish();
	}
	
	protected boolean validarPass() {
		// TODO Auto-generated method stub
		boolean p_ok = false;
		
		if(!TextUtils.isEmpty(pass.getText().toString().trim()) && 
			pass.getText().toString().trim().equals( c_pass.getText().toString().trim() ) ){
			p_ok = true;
		}
		
		
		return p_ok;
	}

	private void obtenerPerfil() {
		
		httpHandler handler = new httpHandler();
		String r = handler.get( getString( R.string.ur_servicio ) + "/login/perfil/" + id );
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			usuario = new Usuario(json.getString("email"), "");	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	public class ObtenerPerfil extends AsyncTask<Void, Void, Void>{		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub			
			try{
				obtenerPerfil();
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
				txt_email.setText(getString( R.string.str_email_perfil ) + usuario.getEmail());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.ly_perfil).setVisibility(View.VISIBLE);
		}
		
	}
	
	class GuardarPerfil extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
			findViewById(R.id.ly_perfil).setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				guardarRutina();
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
				
				new AlertDialog.Builder( Perfil.this )
			    .setTitle("Muy bien!")
			    .setMessage("Su Perfli se ha guardado con éxito. Recuerde que le avisaremos media hora antes.")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        }
			     })				    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.ly_perfil).setVisibility(View.VISIBLE);
		}
		
	}

	public void guardarRutina() {
		// TODO Auto-generated method stub
		try {						
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("accion","guardarPerfil"));
			data.add(new BasicNameValuePair("id_user", id + "" ));
			data.add(new BasicNameValuePair("password", pass.getText().toString()));
			data.add(new BasicNameValuePair("email", usuario.getEmail()));
			
			httpHandler handler = new httpHandler();
			String r = handler.post( getString( R.string.ur_servicio ) + "/login/perfil/actualizar" , data );
			
			Log.d( getString( R.string.app_name ), "respuesta: " + r );					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
