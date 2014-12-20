package co.foxdigitalst.redeactiva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class configuracionDB extends SQLiteOpenHelper {
	
	private String sql = "create table user_redeactiva(codigo integer primary key, clave text, valor integer)";
	
	public configuracionDB(Context context, String nombre, CursorFactory factory,
			int version) {
		super(context, nombre, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists configuracion");
		db.execSQL(sql);		
	}

}
