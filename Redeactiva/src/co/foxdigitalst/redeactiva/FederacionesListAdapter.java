package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FederacionesListAdapter extends BaseAdapter {

	private ArrayList<Federacion> federaciones;
	private Activity activity;
	
	public FederacionesListAdapter(Activity act, ArrayList<Federacion> fed) {
		this.activity = act;
		this.federaciones = fed;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.federaciones.size();
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
			convertView = inflater.inflate(R.layout.lista_federaciones, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_nombre = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.direccion = (TextView) convertView.findViewById(R.id.txt_direccion);
		holder.telefono = (TextView) convertView.findViewById(R.id.txt_entidad);
		
		Federacion f = federaciones.get(position);		
		holder.txt_nombre.setText(f.getNombre());
		holder.telefono.setText(f.getTelefono());
		holder.direccion.setText(f.getDireccion());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView txt_nombre, direccion, telefono;
	}

}
