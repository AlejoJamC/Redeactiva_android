package co.foxdigitalst.redeactiva;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventosListAdapter extends BaseAdapter {

	private List<Evento> eventos;
	private Activity activity;
	
	public EventosListAdapter(Activity act, List<Evento> eventos) {
		this.activity = act;
		this.eventos = eventos;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.eventos.size();
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
			convertView = inflater.inflate(R.layout.lista_eventos, parent, false);
			holder = new ViewHolder();
			
			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}
		
		String titulo = activity.getString(R.string.str_titulo_evento);
		String horario = activity.getString(R.string.str_horario);
		String entidad = activity.getString(R.string.str_entidad);
		String tipo = activity.getString(R.string.str_tipo_evento);
		String clase = activity.getString(R.string.str_clase);
		
		
		holder.titulo = (TextView) convertView.findViewById(R.id.txt_titulo);
		holder.descripcion = (TextView) convertView.findViewById(R.id.txt_descripcion);
		holder.entidad = (TextView) convertView.findViewById(R.id.txt_entidad);
		holder.horario = (TextView) convertView.findViewById(R.id.txt_horario);
		holder.tipo = (TextView) convertView.findViewById(R.id.txt_tipo);
		holder.clase = (TextView) convertView.findViewById(R.id.txt_clase);
		
		Evento evento = eventos.get(position);		
		holder.titulo.setText(titulo + evento.getNombre());
		holder.descripcion.setText(evento.getDescripcion());
		holder.entidad.setText(entidad + " " +evento.getEntidad());
		holder.horario.setText( horario + "Inicia: "+ evento.getFechaFinal() 
				+ " - Termina: " + evento.getFechaFinal() );
		holder.tipo.setText(tipo + evento.getTipo());
		holder.clase.setText(clase + evento.getClase());
		
		return convertView;
	}
	
	public void setData(List<Evento> ev){
        this.eventos.addAll(ev);
        this.notifyDataSetChanged();
    }
	
	static class ViewHolder {
		TextView titulo, descripcion, tipo, horario, entidad, clase;
	}
}
