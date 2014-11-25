package com.example.tp04;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.example.tp04putain.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTrackActivity extends Activity {
	//Setting des variables de classes Utiles
		MainActivity main;
		Context context;
		Button btArreter;
		Button btCommencer;
		AnalogClock analogclock;
		TextView inOuOutTxt;
		static public long tempsDebut;
		static public long tempsFin;
		/*
		 *  Types d'evenements
		 * 	Type 0 = Undefined Event Type (or RAW)
		 *  Type 1 = PunchIn
		 *  Type 2 = PunchOut Event
		 *  Type 3 = Comment Or Note Event
		 *  Type 4 = other
		 * 
		 */
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_track);
		analogclock = (AnalogClock)findViewById(R.id.analogClock1);
		btCommencer = (Button)findViewById(R.id.PIbuttonl);
		btArreter = (Button)findViewById(R.id.PObuttonL);
		inOuOutTxt =(TextView)findViewById(R.id.textInOuOut);
		inOuOutTxt.setTextColor(Color.RED);
		//Par défaut à la création il est désactivé
		if(PunchedIn()){
		btArreter.setEnabled(true);
		btCommencer.setEnabled(false);
		inOuOutTxt.setText("IN");
		}
		else{
			btArreter.setEnabled(false);
			btCommencer.setEnabled(true);
			inOuOutTxt.setText("OUT");
		}
		this.context = this;
	//	setBTstatus(); // Mettre à on ou a off les boutons
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_track, menu);
		return true;
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
	
	/**
	*
	* @author Charles Perreault, Anthony Pugliese
	*
	* 
	*/
	public void PunchIn(View view)
	{
		long l = System.currentTimeMillis(); //Get le Time Tel Quel
		String s = Long.toString(l); // Le parse
		MainActivity.jdb.newEvent(1, s); // Pi le met dans la Liste
		MainActivity.jdb.saveToDevice(this); // Enregistre la liste sur l'apareil
		this.Toaster("Vous venez de puncher In"); //On affiche à l'utilisateur qu'il a puncher In
		this.setBTstatus(); //Appel la désactivation des boutons
	}
	
	/**
	*
	* @author Charles Perreault, Anthony Pugliese
	*
	* 
	*/
	public void PunchOut(View view)
	{
		long stopTime = System.currentTimeMillis(); //Get le Time 
		String s = Long.toString(stopTime); //le parse
		MainActivity.jdb.newEvent(2, s); //L'enregistre comme type 2 dans la liste
		MainActivity.jdb.saveToDevice(this); //Enregistre la liste sur l'appareil
		this.Toaster("Vous venez de puncher Out"); //On affiche à l'utilisateur qu'il a puncher 
		this.setBTstatus(); //Appel la désactivation des boutons
		try {
			//Affichage d'une Toast avec le temps travaillé
			Context context = getApplicationContext();
			CharSequence text = ("Vous avez travaillé : " + getDiffTime());
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.TOP, 0, 15);
			toast.show();
		} catch (Exception e) {
		}
	}
	
	/**
	*
	* @author Charles Perreault, Michael Carignan-Jacob
	*
	* Calcule le temps du punch IN.
	*/	
	private String getDiffTime()
	{
		long time;
		time = (tempsFin - tempsDebut);
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
	    cal.setTimeInMillis(time);
	    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    String date = DateFormat.format("mm:ss", cal).toString();
		return date;
	}
	
	/**
	*
	* @author Anthony Pugliese, Michael Carignan-Jacob
	*
	* Retourne true si le dernier élément de la liste est un punch In
	*/
	private boolean PunchedIn(){
		if(!MainActivity.jdb.JournaldeBord.isEmpty()){//Si
			evenementJournal dernierEnevement =	MainActivity.jdb.findLastEvent();
			if(dernierEnevement.type == 1){ //c'est un punch in
				tempsDebut = System.currentTimeMillis();
				return true;
			}else
				if(dernierEnevement.type == 3){ //Il vient d'écrire dans le journal, donc il est déjà punch IN.
					return true;
				}
		}
		return false;
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* 
	*/
	private void Toaster(String s)
	{
		Toast toast;
		Context context = this;
		toast = Toast.makeText(context, s,1); // on set le texte
		toast.setGravity(Gravity.TOP, 0, 15); // on set la position
		toast.show(); // 
	}
	
	/**
	*
	* @author Charles Perreault, Anthony Pugliese, Michael Carignan-Jacob
	*
	* Active ou désactive les boutons Commencer et Arreter, en fonction de si on a punché ou non.
	*/
	private void setBTstatus()
	{
		if (PunchedIn()) { // Dans le cas que l'on aurait punché
			btCommencer.setEnabled(false);
			btArreter.setEnabled(true);
			inOuOutTxt.setText("IN");
		}
		else{ // Dans le cas où on aurait pas punché
			tempsFin = System.currentTimeMillis();
			btCommencer.setEnabled(true);
			btArreter.setEnabled(false);
			inOuOutTxt.setText("OUT");
		}
	}
}
