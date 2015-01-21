package co.foxdigitalst.redeactiva;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

public class NuevaRutina extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	EditText titulo;
	CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
	String dias;
	TimePicker hora;
	boolean dias_req = false;
	int id_user;
	View rootView;
	
	public NuevaRutina() {
	
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.activity_nueva_rutina,
				container, false);
		
		titulo = (EditText)rootView.findViewById(R.id.et_titulo);
		
		lunes = (CheckBox)rootView.findViewById(R.id.ck_lunes);
		martes = (CheckBox)rootView.findViewById(R.id.ck_martes);
		miercoles = (CheckBox)rootView.findViewById(R.id.ck_miercoles);
		jueves = (CheckBox)rootView.findViewById(R.id.ck_jueves);
		viernes = (CheckBox)rootView.findViewById(R.id.ck_viernes);
		sabado = (CheckBox)rootView.findViewById(R.id.ck_sabado);
		domingo = (CheckBox)rootView.findViewById(R.id.ck_domingo);
		
		hora = (TimePicker)rootView.findViewById(R.id.tp_hora);
		
		id_user = getArguments().getInt(ARG_SECTION_NUMBER);	
		
		rootView.findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean titulo_ok = validarTitulo();
				boolean dias_ok = validarDias();
				
				if(!titulo_ok || !dias_ok){
					new AlertDialog.Builder(getActivity())
				    .setTitle("Campos Obligatorios")
				    .setMessage("Verifique que haya ingresado un título y seleccionado por lo menos un día y vuelva a intentarlo..")
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
		
		return rootView;
		
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
			data.add(new BasicNameValuePair("accion","nuevaRutina"));
			data.add(new BasicNameValuePair("userId", usuario + "" ));
			data.add(new BasicNameValuePair("titulo_rutina", titulo.getText().toString()));			
			data.add(new BasicNameValuePair("hora", hora_post ));
			data.add(new BasicNameValuePair("dias", dias ));
			
			httpHandler handler = new httpHandler();
			String r = handler.post( getString( R.string.ur_servicio2 ) + "/rutina.php" , data );
			
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
			rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
			rootView.findViewById(R.id.sv_rutinas).setVisibility(View.GONE);
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
				
				new AlertDialog.Builder(getActivity())
			    .setTitle("Muy bien!")
			    .setMessage("Su Rutina se ha guardado con éxito. Recuerde que le avisaremos media hora antes.")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        }
			     })				    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
				
				lunes.setChecked(false);
				martes.setChecked(false);
				miercoles.setChecked(false);
				jueves.setChecked(false);
				viernes.setChecked(false);
				sabado.setChecked(false);
				domingo.setChecked(false);
				
				titulo.setText("");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			rootView.findViewById(R.id.loading).setVisibility(View.GONE);
			rootView.findViewById(R.id.sv_rutinas).setVisibility(View.VISIBLE);
		}
		
	}

}
