package co.foxdigitalst.redeactiva;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeportistasListAdapter extends BaseAdapter {
	private List<Historia> historias;
	private Activity activity;
	public ImageLoader imageLoader;
	
	public DeportistasListAdapter(Activity act, List<Historia> historias) {
		this.activity = act;
		imageLoader = new ImageLoader(act);
		this.historias = historias;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.historias.size();
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
			convertView = inflater.inflate(R.layout.lista_historias, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_titulo = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.img_v = (ImageView) convertView.findViewById(R.id.img);		
		
		Historia historia = historias.get(position);		
		holder.txt_titulo.setText(historia.getDescripcion());		
		imageLoader.DisplayImage(historia.getAdjunto(), holder.img_v);
		
		return convertView;
	}
	
	public void setData(List<Historia> h){
        this.historias.addAll(h);
        this.notifyDataSetChanged();
    }
		
	static class ViewHolder {
		TextView txt_titulo;
		ImageView img_v;
	}
}
