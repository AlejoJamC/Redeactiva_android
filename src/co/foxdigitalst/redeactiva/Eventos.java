package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Eventos extends Activity {

	List<Evento> eventos;
	EventosListAdapter eventosAdapter;
	ListView lv_eventos;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventos);
		
		lv_eventos = (ListView) findViewById(R.id.lista_eventos);	
		
		eventos = new ArrayList<Evento>();
		
		final ProgressBar progressBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyle);
        LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(progressBarParams);
        progressBar.setPadding(6, 6, 6, 6);
        progressBar.setVisibility(View.INVISIBLE);

        LinearLayout footerLinearLayout = new LinearLayout(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        footerLinearLayout.setGravity(Gravity.CENTER);
        footerLinearLayout.setLayoutParams(layoutParams);
        footerLinearLayout.addView(progressBar);
		
		lv_eventos.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	new ObtenerMasEventos(progressBar,Eventos.this,eventosAdapter).execute(page);
            }
        });
		
		lv_eventos.addFooterView(footerLinearLayout);
		
		eventosAdapter = new EventosListAdapter(Eventos.this, eventos);
		 
        // setting list adapter
		lv_eventos.setAdapter(eventosAdapter);
        
		new ObtenerMasEventos(progressBar,Eventos.this,eventosAdapter).execute(1);
        
		lv_eventos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				final int p = position;
				ArrayList<String> itemsList = new ArrayList<String>();
				
				if(eventos.get(p).getWeb().trim().length() > 0){
					itemsList.add("Ver Sitio Web");
				}
				
				if(eventos.get(p).getEmail().trim().length() > 0){
					itemsList.add("Enviar Email");
				}
				
				final String[] items = itemsList.toArray( new String[itemsList.size()] );
				

				AlertDialog.Builder builder = new AlertDialog.Builder(Eventos.this);
				builder.setTitle(eventos.get(position).getNombre());
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	if(items[item] == "Ver Sitio Web"){
				    		if(eventos.get(p).getWeb().trim().length() > 0){
				    			String url = eventos.get(p).getWeb();
				    			Intent i = new Intent(Intent.ACTION_VIEW);
				    			i.setData(Uri.parse(url));
				    			startActivity(i);
				    		}
				    	}else if(items[item] == "Enviar Email"){
				    		if(eventos.get(p).getEmail().trim().length() > 0){
				    			String url = "mailto:"+eventos.get(p).getEmail();
				    			Intent i = new Intent(Intent.ACTION_VIEW);
				    			i.setData(Uri.parse(url));
				    			startActivity(i);
				    		}
				    	}
				    	
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			
		});
		
	}

}
