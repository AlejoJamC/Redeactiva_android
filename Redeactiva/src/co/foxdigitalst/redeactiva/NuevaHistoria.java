package co.foxdigitalst.redeactiva;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class NuevaHistoria extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	EditText titulo;
	ImageView img;
	int id_user;
	View rootView;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private Bitmap captura;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.activity_nueva_historia,
				container, false);
		
		titulo = (EditText)rootView.findViewById(R.id.et_titulo);
		img = (ImageView)rootView.findViewById(R.id.img);
		id_user = getArguments().getInt(ARG_SECTION_NUMBER);	
		
		rootView.findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean titulo_ok = validarTitulo();
				
				if(!titulo_ok){
					new AlertDialog.Builder(getActivity())
				    .setTitle("Campos Obligatorios")
				    .setMessage("Verifique que haya ingresado un titulo y haya tomado una foto. vuelva a intentarlo..")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	dialog.cancel();
				        }
				     })				    
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
					
				}else{
					guardarBitmap();
				}
				
			}
		});
		
		rootView.findViewById(R.id.btn_captura).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dispatchTakePictureIntent();
			}
		});
		
		return rootView;
		
	}
	
	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	protected boolean validarTitulo() {
		// TODO Auto-generated method stub
		boolean t_ok = false;
		
		if(!TextUtils.isEmpty(titulo.getText().toString().trim())){
			t_ok = true;
		}
		
		if(titulo.getText().toString().trim().length() > 0){
			t_ok = true;
		}
		
		
		return t_ok;
	}
		
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    getActivity();
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");	        
	        
	        int screenWidth = 768;
            int screenHeight = 768;
                        
            Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, screenWidth,screenHeight , true);
            imageBitmap=scaled;
            
            captura = imageBitmap;
            img.setVisibility(View.VISIBLE);
	        img.setImageBitmap(captura);
	        
	        getActivity().findViewById(R.id.btn_guardar).setEnabled(true);
	    }
	}
	
	@SuppressLint("SimpleDateFormat")
	public void guardarBitmap(){

    	Date fecha = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String fechaHora = format.format(fecha);
		
		File imagesFolder = new File(Environment.getExternalStorageDirectory(), "redeactiva");
        if(!imagesFolder.exists())
        imagesFolder.mkdirs();   
        String n = "image_" + fechaHora + ".jpg";
        File pictureFile = new File(imagesFolder, n );
        
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            
            captura.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("No File", "File not found: " + e.getMessage());
        } catch (IOException e) {
            //Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        
        //Toast.makeText(getApplicationContext(), "Se ha guardado su foto correctamente. ahora la puedes compartir..", Toast.LENGTH_LONG).show();
        String t = Environment.getExternalStorageDirectory() + "/redeactiva/"+n;
                
		new subirFoto().execute(t);
		
	}
		
	public boolean uploadFile(String filename){
        try {
            FileInputStream fis = new FileInputStream(filename);
            
            String urlUpload = "";
			try {
				urlUpload = getString( R.string.ur_servicio ) + "/historias/" + id_user + "/nueva?desc="+ 
						URLEncoder.encode(titulo.getText().toString(), "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            Log.d("ArtCreative","url upload: "+urlUpload);
            HttpFileUploader htfu = new HttpFileUploader(urlUpload,"noparamshere", filename);
            htfu.doStart(fis);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;        
        
    }
	
	class subirFoto extends AsyncTask<String, Void, Boolean>{		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			rootView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
			rootView.findViewById(R.id.sv_nhistoria).setVisibility(View.GONE);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			return uploadFile(params[0]);
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try {
				
				if(result){
					new AlertDialog.Builder(getActivity())
				    .setTitle("Muy bien!")
				    .setMessage("Su Historia se ha guardado con éxito.")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	dialog.cancel();
				        }
				     })				    
				    .setIcon(android.R.drawable.ic_dialog_alert)
				    .show();
					
					img.setVisibility(View.GONE);
			        getActivity().findViewById(R.id.btn_guardar).setEnabled(false);
			        titulo.setText("");
					 
				}				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
			
			rootView.findViewById(R.id.loading).setVisibility(View.GONE);
			rootView.findViewById(R.id.sv_nhistoria).setVisibility(View.VISIBLE);
		}
		
		
	}
	

}
