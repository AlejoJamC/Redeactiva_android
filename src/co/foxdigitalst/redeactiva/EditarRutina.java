package co.foxdigitalst.redeactiva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditarRutina extends Activity {

	public static final String ARG_SECTION_NUMBER = "section_number";
	EditText titulo;
	CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
	String dias;
	TimePicker hora;
	boolean dias_req = false;
	int id_user;
	String id_rutina;
	Rutina rutina;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_editar_rutina);
		
		titulo = (EditText)findViewById(R.id.et_titulo);
		
		lunes = (CheckBox)findViewById(R.id.ck_lunes);
		martes = (CheckBox)findViewById(R.id.ck_martes);
		miercoles = (CheckBox)findViewById(R.id.ck_miercoles);
		jueves = (CheckBox)findViewById(R.id.ck_jueves);
		viernes = (CheckBox)findViewById(R.id.ck_viernes);
		sabado = (CheckBox)findViewById(R.id.ck_sabado);
		domingo = (CheckBox)findViewById(R.id.ck_domingo);
		
		hora = (TimePicker)findViewById(R.id.tp_hora);
		
		Intent intent = getIntent();
		id_user = intent.getIntExtra("id_user", 0);
		id_rutina = intent.getStringExtra("id_rutina");
		
		if(id_user>0 && !TextUtils.isEmpty(id_rutina)){
			new ObtenerRutina().execute();
		}
		
		findViewById(R.id.btn_eliminar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EliminarRutina().execute();
			}
		});
		
		findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean titulo_ok = validarTitulo();
				boolean dias_ok = validarDias();
				
				if(!titulo_ok || !dias_ok || id_user<=0 || TextUtils.isEmpty(id_rutina)){
					new AlertDialog.Builder( EditarRutina.this )
				    .setTitle("Campos Obligatorios")
				    .setMessage("Verifique que haya ingresado un titulo y seleccionado por lo menos un día y vuelva a intentarlo..")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	dialog.cancel();
				        }
				     })				    
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
					
				}else{
					new GuardarRutina().execute(id_user);
				}
				
			}
		});
	}	
	
	private void obtenerRutina() {
		
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("accion","rutina"));
		data.add(new BasicNameValuePair("objectId", id_rutina ));
		
		httpHandler handler = new httpHandler();
		String r = handler.post( getString( R.string.ur_servicio ) + "/rutina.php" , data );
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try { 
			JSONObject json = new JSONObject(r);
			JSONObject valores = json.getJSONObject("rutina");
			
			rutina= new Rutina(valores.getString("objectId"), valores.getString("titulo_rutina"), 
					valores.getString("dias"), valores.getString("hora"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class ObtenerRutina extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				obtenerRutina();
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
				titulo.setText(rutina.getTitulo());
				
				String[] hora_temp = rutina.getHora().split(" ");
				String[] hora_temp1 = hora_temp[1].split(":");
				int h = Integer.parseInt( hora_temp1[0] );
				int min = Integer.parseInt( hora_temp1[1] );

				hora.setCurrentHour(h);
				hora.setCurrentMinute(min);
				
				if(rutina.getDias().contains("Lunes")){
					lunes.setChecked(true);
				}
				
				if(rutina.getDias().contains("Martes")){
					martes.setChecked(true);
				}
				
				if(rutina.getDias().contains("Miercoles") || rutina.getDias().contains("Miércoles") ){
					miercoles.setChecked(true);
				}
				
				if(rutina.getDias().contains("Jueves")){
					jueves.setChecked(true);
				}
				
				if(rutina.getDias().contains("Viernes")){
					viernes.setChecked(true);
				}
				
				if(rutina.getDias().contains("Sabado") || rutina.getDias().contains("Sábado")){
					sabado.setChecked(true);
				}
				
				if(rutina.getDias().contains("Domingo")){
					domingo.setChecked(true);
				}


				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			findViewById(R.id.loading).setVisibility(View.GONE);
			findViewById(R.id.sv_rutinas).setVisibility(View.VISIBLE);
				        
		}
		
	}

	protected boolean validarDias() {
		// TODO Auto-generated method stub
		dias = "";
		dias_req = false;
		if(lunes.isChecked()){
			dias_req = true;
			dias += "Lunes";
			
		}
		
		if(martes.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Martes";
			}else{
				dias += "Martes";
			}			
			
		}
		
		if(miercoles.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Miercoles";
			}else{
				dias += "Miercoles";
			}			
			
		}
		
		if(jueves.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Jueves";
			}else{
				dias += "Jueves";
			}			
			
		}
		
		if(viernes.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Viernes";
			}else{
				dias += "Viernes";
			}			
			
		}
		
		if(sabado.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Sabado";
			}else{
				dias += "Sabado";
			}			
			
		}
		
		if(domingo.isChecked()){
			dias_req = true;
			if(dias.length() > 0){
				dias += ", Domingo";
			}else{
				dias += "Domingo";
			}			
			
		}
		
		return dias_req;
	}

	protected boolean validarTitulo() {
		// TODO Auto-generated method stub
		boolean t_ok = false;
		
		if(!TextUtils.isEmpty(titulo.getText().toString().trim())){
			t_ok = true;
		}
		
		if(titulo.getText().toString().trim().length() > 0){
			t_ok = true;
		}
		
		
		return t_ok;
	}
	
	@SuppressLint("SimpleDateFormat")
	private void guardarRutina(int usuario) {
									
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String hora_post = sdf.format(new Date());
			
			hora_post += " " + hora.getCurrentHour() + ":" + hora.getCurrentMinute() + ":00 GMT-5";
			
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("accion","modificarRutina"));
			data.add(new BasicNameValuePair("userId", usuario + "" ));
			data.add(new BasicNameValuePair("titulo_rutina", titulo.getText().toString()));			
			data.add(new BasicNameValuePair("hora", hora_post ));
			data.add(new BasicNameValuePair("dias", dias ));
			data.add(new BasicNameValuePair("objectId", id_rutina ));
			
			httpHandler handler = new httpHandler();
			String r = handler.post( getString( R.string.ur_servicio ) + "/rutina.php" , data );
			
			Log.d( getString( R.string.app_name ), "respuesta: " + r );					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class GuardarRutina extends AsyncTask<Integer, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
			findViewById(R.id.sv_rutinas).setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try{
				guardarRutina(params[0]);
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
				
				new AlertDialog.Builder( EditarRutina.this )
			    .setTitle("Muy bien!")
			    .setMessage("Su Ritina se ha guardado con éxito. Recuerde que le avisaremos media hora antes.")
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
			findViewById(R.id.sv_rutinas).setVisibility(View.VISIBLE);
		}
		
	}
	
	private void EliminarRutina() {
		
		try {						
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("accion","eliminarRuta"));
			data.add(new BasicNameValuePair("objectId", id_rutina ));
			
			httpHandler handler = new httpHandler();
			String r = handler.post( getString( R.string.ur_servicio ) + "/rutina.php" , data );
			
			Log.d( getString( R.string.app_name ), "respuesta: " + r );					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }
	
	class EliminarRutina extends AsyncTask<Integer, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
			findViewById(R.id.sv_rutinas).setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try{
				EliminarRutina();
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
				
				new AlertDialog.Builder( EditarRutina.this )
			    .setTitle("Muy bien!")
			    .setMessage("Su Ritina se ha eliminado con éxito.")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        	Intent a = new Intent(EditarRutina.this,Rutinas.class);
			            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			            startActivity(a);
			        }
			     })				    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}			
		}
		
	}

}
