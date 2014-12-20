package co.foxdigitalst.redeactiva;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeportesListAdapter extends BaseAdapter {
	
	private List<Deporte> deportes;
	private Activity activity;
	public ImageLoader imageLoader;
	
	public DeportesListAdapter(Activity act, List<Deporte> dep) {
		this.activity = act;
		imageLoader = new ImageLoader(act);
		this.deportes = dep;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.deportes.size();
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
			convertView = inflater.inflate(R.layout.lista_deportes, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_titulo = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.img_dep = (ImageView) convertView.findViewById(R.id.img_dep);		
		
		Deporte d = deportes.get(position);		
		holder.txt_titulo.setText(d.getNombre());
		
		imageLoader.DisplayImage(d.getLogo(), holder.img_dep);
		
		Log.d("ac","Fin elemento "+position);
		return convertView;
	}
	
	public void setData(List<Deporte> dep){
        this.deportes.addAll(dep);
        this.notifyDataSetChanged();
    }
	
	static class ViewHolder {
		TextView txt_titulo;
		ImageView img_dep;
	}

}
