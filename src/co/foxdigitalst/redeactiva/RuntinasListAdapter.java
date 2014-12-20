package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RuntinasListAdapter extends BaseAdapter {

	private ArrayList<Rutina> rutinas;
	private Activity activity;
	
	public RuntinasListAdapter(Activity act, ArrayList<Rutina> r) {
		this.activity = act;
		this.rutinas = r;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.rutinas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){ 
			LayoutInflater inflater = (LayoutInflater) activity 
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.lista_rutinas, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_nombre = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.txt_dias = (TextView) convertView.findViewById(R.id.txt_fechas);
		
		Rutina r = rutinas.get(position);	
		
		String[] hora_temp = r.getHora().split(" ");
		String[] hora_temp1 = hora_temp[1].split(":");
		int h = Integer.parseInt( hora_temp1[0] );
		int min = Integer.parseInt( hora_temp1[1] );
		String siglas = "a.m";
		
		if( h> 12 ){
			h = h-12;
			siglas = "p.m";
		}
		
		holder.txt_nombre.setText(r.getTitulo() + " a las " + h + ":" + min + " " + siglas);
		holder.txt_dias.setText(r.getDias());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_nombre, txt_dias;
	}

}
