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

public class VideosListAdapater extends BaseAdapter {

	private List<VideoYoutube> videosY;
	private Activity activity;
	public ImageLoader imageLoader;
	
	public VideosListAdapater(Activity act, List<VideoYoutube> vds) {
		this.activity = act;
		imageLoader = new ImageLoader(act);
		this.videosY = vds;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.videosY.size();
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
			convertView = inflater.inflate(R.layout.list_video, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_titulo = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.img_v = (ImageView) convertView.findViewById(R.id.img);		
		
		VideoYoutube v = videosY.get(position);		
		holder.txt_titulo.setText(v.getNombre());
		
		imageLoader.DisplayImage(v.getImg(), holder.img_v);
		
		return convertView;
	}
	
	public void setData(List<VideoYoutube> v){
        this.videosY.addAll(v);
        this.notifyDataSetChanged();
    }
	
	static class ViewHolder {
		TextView txt_titulo;
		ImageView img_v;
	}

}
