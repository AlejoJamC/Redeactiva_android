package co.foxdigitalst.redeactiva;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GaleriasAdapter extends BaseAdapter {

	private ArrayList<Galeria> galerias;
	private Activity activity;
	public ImageLoader imageLoader;
	
	public GaleriasAdapter(Activity act, ArrayList<Galeria> gs) {
		this.activity = act;
		imageLoader = new ImageLoader(act);
		this.galerias = gs;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.galerias.size();
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
			convertView = inflater.inflate(R.layout.lista_galerias, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.img_dep = (ImageView) convertView.findViewById(R.id.img);		
		
		Galeria g = galerias.get(position);		
		
		imageLoader.DisplayImage(g.getUrl(), holder.img_dep);
		
		Log.d("ac","Fin elemento "+position);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView img_dep;
	}

}
