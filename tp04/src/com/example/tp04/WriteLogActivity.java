package com.example.tp04;

import com.example.tp04putain.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WriteLogActivity extends Activity {
	EditText mEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_log);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_log, menu);
		return true;
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* Retourne dans l'activite principale
	*/
	public void ReturnToMain(View view) {
    	Intent intent = new Intent (this, MainActivity.class); 
    	startActivity(intent);
    }
	
	/**
	*
	* @author Charles Perreault
	*
	* Ajoute une note dans le journal de bord et affiche un toast avec le nombre d'evenements
	*/
	public void addNote(View view) {
		mEdit = (EditText)findViewById(R.id.wl_tb_text);
		/*
		 * 	Type 0 = Undefined Event Type (or RAW)
		 *  Type 1 = PunchIn Event
		 *  Type 2 = PunchOut Event
		 *  Type 3 = Comment Or Note Event
		 *  Type 4 = other
		 */
		
		MainActivity.jdb.newEvent(3, mEdit.getText().toString()); //Ajoute un élément de Type 3 au Journal
		
		MainActivity.jdb.saveToDevice(this); //Écrit le journal sur l'appareil
		
		//Affiche le nombre d'évènements dans le journal dans un toast
		Toast toast;
		Context context = this;
		String text;
		text = ("Nombre d'evenement au Journal : " + String.valueOf(MainActivity.jdb.size()));
		toast = Toast.makeText(context, text, 1);
		toast.setGravity(Gravity.TOP, 0, 15);
		toast.show();
		this.ReturnToMain(view);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
