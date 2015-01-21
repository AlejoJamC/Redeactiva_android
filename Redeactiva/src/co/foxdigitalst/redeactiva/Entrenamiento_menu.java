package co.foxdigitalst.redeactiva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Entrenamiento_menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrenamiento_menu);
		
		findViewById(R.id.btn_video).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Entrenamiento_menu.this, Entrenamiento.class);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.btn_reto).setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Entrenamiento_menu.this, Frases.class);
				in1.putExtra("FRASE", 1);
				startActivity(in1);
			}
		});
		
		findViewById(R.id.btn_frase_dia).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Entrenamiento_menu.this, Frases.class);
				in1.putExtra("FRASE", 0);
				startActivity(in1);
			}
		});
		
	}
}
