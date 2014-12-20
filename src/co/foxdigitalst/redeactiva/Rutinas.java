package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Rutinas extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	int id_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rutinas);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		Intent intent = getIntent();
		id_user = intent.getIntExtra("id_user", 0);		

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(getActionBarThemedContextCompat(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								"Ver Rutinas",
								"Nueva Rutina", }), this);
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		if(position == 0){
			Fragment fragment = new DummySectionFragment();	
			
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, id_user);
			fragment.setArguments(args);
			
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}else{
			Fragment fragment = new NuevaRutina();	
			
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, id_user);
			fragment.setArguments(args);
			
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
			
		}
		
		return true;
	}
	
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		ArrayList<Rutina> rutinas;
		RuntinasListAdapter listaRutinas;
		ListView lv_rutinas;
		int id_user;
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_rutinas_dummy,
					container, false);
			
			id_user = getArguments().getInt(ARG_SECTION_NUMBER);
						
			lv_rutinas = (ListView) rootView
					.findViewById(R.id.lv_rutinas);
			
			lv_rutinas.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent in1 = new Intent(getActivity().getApplicationContext(), EditarRutina.class);
					
					in1.putExtra("id_user", id_user);
					in1.putExtra("id_rutina", rutinas.get(position).getObjectId());
					startActivity(in1);					
				}
			}); 
			
			new ObtenerRutinas().execute(id_user);
			return rootView;
		}
		
		private void obtenerRutinas(int usuario) {
			rutinas = new ArrayList<Rutina>();
								
			try {
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("accion","rutinas"));
				data.add(new BasicNameValuePair("userId", usuario + "" ));
				
				httpHandler handler = new httpHandler();
				String r = handler.post( getString( R.string.ur_servicio ) + "/rutina.php" , data );
				
				Log.d( getString( R.string.app_name ), "respuesta: " + r );
				
				JSONObject json = new JSONObject(r);
				JSONArray valores = json.getJSONArray("rutinas");
				
				for (int i = 0; i < valores.length(); i++) {
				    JSONObject object = valores.getJSONObject(i); 
				    Rutina rutina = new Rutina(object.getString("objectId"), object.getString("titulo_rutina"), 
				    		object.getString("dias"), object.getString("hora"));
				    
				   	rutinas.add(rutina);			  
				}				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    }
		
		class ObtenerRutinas extends AsyncTask<Integer, Void, Void>{

			@Override
			protected Void doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				try{
					obtenerRutinas(params[0]);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				try {
					if(rutinas.size() > 0){
						listaRutinas = new RuntinasListAdapter(getActivity(), rutinas);
						// setting list adapter
				        lv_rutinas.setAdapter(listaRutinas);
				        lv_rutinas.setVisibility(View.VISIBLE);	
					}else{
							
						getActivity().findViewById(R.id.txt_msg).setVisibility(View.VISIBLE);
					}
					
			        	        
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace(); 
				}
				
				getActivity().findViewById(R.id.loading).setVisibility(View.GONE);
				
		        
			}
			
		}
	}

}
