package co.foxdigitalst.redeactiva;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Registro extends Activity {

	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	private String mPassword2;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mPasswordView2;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	private int id_user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		// Set up the login form.
				mEmailView = (EditText) findViewById(R.id.email);

				mPasswordView = (EditText) findViewById(R.id.password);
				mPasswordView2 = (EditText) findViewById(R.id.password_c);
				
				mPasswordView2
						.setOnEditorActionListener(new TextView.OnEditorActionListener() {
							@Override
							public boolean onEditorAction(TextView textView, int id,
									KeyEvent keyEvent) {
								if (id == R.id.login || id == EditorInfo.IME_NULL) {
									attemptLogin();
									return true;
								}
								return false;
							}
						});

				mLoginFormView = findViewById(R.id.login_form);
				mLoginStatusView = findViewById(R.id.login_status);
				mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

				findViewById(R.id.sign_in_button).setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								attemptLogin();
							}
						});
				
	}
	
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mPasswordView2.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPassword2 = mPasswordView2.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword2)) {
			mPasswordView2.setError(getString(R.string.error_field_required));
			focusView = mPasswordView2;
			cancel = true;
		} else if (mPassword2.length() < 4) {
			mPasswordView2.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView2;
			cancel = true;
		}
		
		if(!mPassword.equals(mPassword2)){
			mPasswordView2.setError("Contrase�as no coinciden");
			focusView = mPasswordView2;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText("Un Momento...");
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private boolean loginIntent() {						
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("password", mPassword));
		data.add(new BasicNameValuePair("email", mEmail));
		
		httpHandler handler = new httpHandler();
		String r = handler.post( getString( R.string.ur_servicio ) + "/login/crear" , data );
		
		Log.d( getString( R.string.app_name ), "respuesta: " + r );
		
		try {
			JSONObject json = new JSONObject(r);
			if(json.getString("respuesta").equals("exito")){
				id_user = json.getInt("id");
				altauser();				
				return true;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		 
    }
	
	public void altauser() {
        configuracionDB admin = new configuracionDB(Registro.this,
                "redeactiva", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        
        ContentValues registro = new ContentValues();
        registro.put("codigo", "1"); 
        registro.put("clave", "empleado_id");
        registro.put("valor", id_user);
        bd.insert("user_redeactiva", null, registro);
        
        Log.d( getString( R.string.app_name ) , "Ingres� user_id correctamente: "+id_user);
        
        bd.close();       
       
        Parse.initialize(Registro.this, "zBwe2MB38Zsn3F2zRL29NYJiXtalKZ8rsB1IEd0j", "5YqqoUsMY96M9d8OTeuC9nY19LChMKqRgkI35IF0");				
		ParseInstallation install = ParseInstallation.getCurrentInstallation();
        
        ArrayList<String> channels = new ArrayList<String>();
        channels.add("User_"+id_user);
        install.put("channels", channels);
        install.saveEventually();
	}

	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			boolean resultado = loginIntent();
			
			// TODO: register the new account here.
			return resultado;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
							
				Intent i = new Intent(Registro.this, Principal.class);
				i.putExtra("id_user", id_user);
		        startActivity(i);
				finish();
			} else {
				new AlertDialog.Builder( Registro.this )
			    .setTitle("Algo sali� mal!")
			    .setMessage("Lo sentimos no se pudo completar su petici�n... verifique todos los campos e intente de nuevo m�s tarde")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        }
			     })				    
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	
}
