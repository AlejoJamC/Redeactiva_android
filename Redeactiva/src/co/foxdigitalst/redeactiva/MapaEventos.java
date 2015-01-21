package co.foxdigitalst.redeactiva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaEventos extends FragmentActivity {

	GoogleMap map;
	GoogleMapOptions options = new GoogleMapOptions();
	SupportMapFragment fragMap;
	private String menu_latitud;
	private String menu_longitud;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa_eventos);
		
		Intent iGet = getIntent(); 
        menu_latitud = iGet.getStringExtra("latitud");
        menu_longitud = iGet.getStringExtra("longitud"); 
        fragMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);       
        
        try {
        	setUpMapIfNeeded();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	 private void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map.
	        if (map == null) {
	            // Try to obtain the map from the SupportMapFragment.
	        	map = fragMap.getMap();
	            // Check if we were successful in obtaining the map.
	            if (map != null) {
	                setUpMap();
	                showLocation(); 
	                
	            }
	        }
	    }
	    
	 // method to set up map
	    void setUpMap(){
	    	options.mapType(GoogleMap.MAP_TYPE_NORMAL);
			options.compassEnabled(true);
			options.rotateGesturesEnabled(true);
			options.tiltGesturesEnabled(true);
			options.zoomControlsEnabled(true);
			SupportMapFragment.newInstance(options);
			
		}
	    
	    void showLocation(){

	    	// get restaurant latitude & longitude from strings.xml
	    	double Latitude = Double.parseDouble(menu_latitud);
	    	double Longitude = Double.parseDouble(menu_longitud);
				
			LatLng latlng = new LatLng(Latitude, Longitude);
			
			// add marker to the map
			map.addMarker(new MarkerOptions()
				.title("Titulo del mapa")
				.snippet("Dirección")
				.position(latlng)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.map5)
			));
			
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.0f));
		}

}
