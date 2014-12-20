package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Recreativas extends Activity {

	List<Proyecto> proyectos;
	ProyectosListAdapter listaProyectos;
	ListView lv_deportes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recreativas);
		
		lv_deportes = (ListView) findViewById(R.id.lv_deportes);
		
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
		
		lv_deportes.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	new ObtenerMasRecreacion(progressBar,Recreativas.this,listaProyectos).execute(page);
            }
        });
		
		lv_deportes.addFooterView(footerLinearLayout);
		
		proyectos = new ArrayList<Proyecto>();
		listaProyectos = new ProyectosListAdapter(Recreativas.this, proyectos);
		
		// setting list adapter
        lv_deportes.setAdapter(listaProyectos);
		new ObtenerMasRecreacion(progressBar,Recreativas.this,listaProyectos).execute(1);
		
		lv_deportes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent in1 = new Intent(Recreativas.this, DetalleRecreativa.class);
				in1.putExtra("id_deporte", proyectos.get(position).getPartitionKey() + ":" + proyectos.get(position).getRowkey());
				startActivity(in1);
			}
			
		});
		
	}
	
}
