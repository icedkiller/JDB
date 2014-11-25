package com.example.tp04;
import java.util.Calendar;
import java.util.Locale;

import android.text.format.DateFormat;


public class evenementJournal {
	public int ID; //unique
	public long Timestamp;
	public int type;
	/*
	 * 	Type 0 = Undefined Event Type (or RAW)
	 *  Type 1 = PunchIn
	 *  Type 2 = PunchOut Event
	 *  Type 3 = Comment Or Note Event
	 *  Type 4 = other
	 * 
	 */
	public String Data;
	
	//Constructeur
	public evenementJournal(){
		//Il n'y a pas vraiment de constucteur ici puisque on le fait plus tard..
		
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* Retourne une String avec la Date et l'heure
	*/
	private String getDate(long time) {
	    Calendar cal = Calendar.getInstance(Locale.ENGLISH); 
	    cal.setTimeInMillis(time);
	    String date = DateFormat.format("dd-MM-yyyy h:mm:ss a", cal).toString();
	    return date;
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* Retourne une String avec toutes les données non formatées
	*/
	public String toString()
	{
		return (getDate(Timestamp) + " " + String.valueOf(type) + " " + Data);
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* Retourne une String avec toutes les données Formatées en Human Readable
	*/
	public String toHumanReadableString()
	{
		String _timestamp;
		String _type;
		String _data;
		
		//TimeStamp
		_timestamp = getDate(Timestamp);
		
		//Type
		switch (type) {
		case 0:
			_type = "RAW DATA";
			_data = Data;
			break;
		case 1:
			_type = "Punch In";
			_data = getDate(Long.parseLong(Data));
			break;
		case 2:
			_type = "Punch Out";
			_data = getDate(Long.parseLong(Data));
			break;
		case 3:
			_type = "Note";
			_data = Data;
			break;
		case 4:
			_type = "Autre Type";
			_data = Data;
			break;
		default:
			_type = "RAW DATA";
			_data = Data;
			break;
		}
		
		return (_timestamp + " " + _type + " " + _data);
	}
	
	/**
	*
	* @author Charles Perreault
	*
	* Retourne la valeur en string du champ de donnée
	*/
	public String toDataString()
	{
		String dataToReturn;
		dataToReturn = Data;
		//if (type == 1)
			
		return dataToReturn;
	}

	
}
