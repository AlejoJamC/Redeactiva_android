package co.foxdigitalst.redeactiva;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class Principal extends Activity {

	private int id;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("id_user", 0);
		
		findViewById(R.id.deportes_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Deportes.class);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.actrec_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Recreativas.class);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.entrenamiento_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Entrenamiento_menu.class);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.compartir_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
		    	intent.putExtra(Intent.EXTRA_TEXT, "http://www.coldeportes.gov.co/");
		    	startActivity(Intent.createChooser(intent, "Compartir"));
			}
		});
		
		findViewById(R.id.rutina_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Rutinas.class);
				in1.putExtra("id_user", id);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.deportistas_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Historias.class);
				in1.putExtra("id_user", id);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.eventos_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Eventos.class);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.perfil_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Principal.this, Perfil.class);
				in1.putExtra("id_user", id);
				startActivity(in1);
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
		configuracionDB admin = new configuracionDB(Principal.this,
	            "redeactiva", null, 1);
	    SQLiteDatabase bd = admin.getWritableDatabase();        
	    int cant = bd.delete("user_redeactiva", "codigo=1", null);
	    bd.close();
	    
	    if (cant == 1)
	    	Log.d(getString(R.string.app_name), "Se borró Email satisfactoriamente");            
	    else
	    	Log.d(getString(R.string.app_name), "No existia");
		
		Intent i = new Intent(Principal.this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(i);
	    finish();
	}
	
}
