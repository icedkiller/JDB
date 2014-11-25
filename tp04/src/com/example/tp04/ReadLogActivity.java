package com.example.tp04;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.tp04putain.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReadLogActivity extends Activity {
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_log);
		lv = (ListView)findViewById(R.id.rl_lv);
		ArrayList<evenementJournal> listDesEvenements = new ArrayList<evenementJournal>();
		
		for (Iterator<evenementJournal> evenements = MainActivity.jdb.JournaldeBord.iterator(); evenements.hasNext();) {
			evenementJournal event = evenements.next();
			if(event.type == 1){ //Si c'est un punch in on ajoute dans son data [PUNCH IN]
			event.Data = "[PUNCH IN]";
			}
			else if(event.type == 2){
				event.Data = "[PUNCH OUT]";
			}
			listDesEvenements.add(event);
			lv.setAdapter(new ArrayAdapter<evenementJournal>(this, android.R.layout.simple_list_item_1, listDesEvenements));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_log, menu);
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
}
