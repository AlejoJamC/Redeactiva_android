package co.foxdigitalst.redeactiva;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Login().execute();
		
		findViewById(R.id.btn_entrar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
		        startActivity(i);
			}
		});
		
		findViewById(R.id.btn_registrarse).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, Registro.class);
		        startActivity(i);
			}
		});
	}	 
	
	public int consultaLogin() {
		configuracionDB admin = new configuracionDB(MainActivity.this,
                "redeactiva", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        
        
        Cursor fila = bd.rawQuery(
                "SELECT valor FROM user_redeactiva WHERE codigo=1", null);
        
        int respuesta = 0;
        if (fila.moveToFirst()) {
            respuesta = fila.getInt(0);
        } 
        
        bd.close();
        
        return respuesta; 

    }
	
	public class Login extends AsyncTask<Void, Void, Integer>{		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return consultaLogin();
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("Redeactiva", "el codigo es: " + result);
			if(result > 0){				
				Intent i = new Intent(MainActivity.this, Principal.class);
				i.putExtra("id_user", result);
		        startActivity(i);
		        finish();
			}else{
				findViewById(R.id.ly_controls).setVisibility(View.VISIBLE);
			}
			
			
			findViewById(R.id.loading).setVisibility(View.GONE);
		}
		
	}
}
