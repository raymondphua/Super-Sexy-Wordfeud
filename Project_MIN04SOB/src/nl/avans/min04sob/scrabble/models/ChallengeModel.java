package nl.avans.min04sob.scrabble.models;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

 

import nl.avans.min04sob.scrabble.core.CoreModel;
import nl.avans.min04sob.scrabble.core.Query;
import nl.avans.min04sob.scrabble.views.ChallengeView;
 

public class ChallengeModel extends Observable  {
//  String query = "SELECT `Reaktie_type` FROM `Spel`; where(ID INT) values()";
	public static final String STATE_ACCEPTED = "Accepted";
	public static final String STATE_REJECTED = "Rejected";
	public static final String STATE_UNKNOWN = "Unknown";
	public static final String STATE_REQUEST = "Request";
	public static final String STATE_PLAYING = "Playing";
	public ResultSet result = new Query("SELECT `*`;").select();;
	public int spelid; 
	public Timer timer = new Timer();
	public ArrayList <Integer> challengegamenumber = new ArrayList<Integer>();
	public ArrayList <String> challengename = new ArrayList<String>();
	//   to do    spel id moet anders zijn dan column nummer
	public ChallengeModel() throws SQLException
	{
		this.addObserver(new ChallengeView());
	}
	public void controle(String Challengername,String  challegendname) throws SQLException//uitdager
	{
		// hoe kom ik aan challenger name?
		int spelid = 1;// of 1
		
		 
		//// need test break
		boolean alreadychallenged = false;
		while (result.next()) {
			String query = "SELECT `INT ID` FROM `Spel`;";
			String query2 = "SELECT `Account_naam_uitdager` FROM `Spel`;";
				ResultSet dbResult2 =  new Query(query2) .select();
				ResultSet dbResult =  new Query(query) .select();
				if (dbResult.getArray(spelid) == null) 
				{
					while(result.next())
					{
						if (dbResult2.getString(spelid).equals(challengename.get(spelid) )) {
							alreadychallenged =true;
							break;
						}
						spelid++;
					}
					
				}
		 
			spelid++;
			
		}
		if(alreadychallenged==false)//overbodig misschien    hoe sterk is break?
		{
			createChallenge(  Challengername,   challegendname);
			 
		}
	}
		
		public void createChallenge(String Challengername,String  challegendname) throws SQLException//uitdager
		{	
		String query = "INSERT INTO `Spel` (`spelid`,`Toestand_type`,`Account_naam_uitdager` ) VALUES ( '"
				+ spelid
				+ STATE_REQUEST
				+ Challengername
				 
				+ "')  ;";
		//replace
		try {
			new Query(query).exec();
		} catch (SQLException sql) {
			System.out.println(query);
			sql.printStackTrace();
		}
		final int sspelid = spelid;
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				String query1 = "SELECT `Reaktie_type` FROM `Spel`; where(ID INT) values()";
				try {
					ResultSet dbResult =  new Query(query1).select();
					if (dbResult.getString(sspelid) == "Accepted") {
					 
						timer.cancel();
						commandsToChallengeview("3");
						
						//open gui response
					}
					if (dbResult.getString(sspelid) == "Rejected") {
						commandsToChallengeview("2");
						timer.cancel();
					}
				} catch (SQLException sql) {
					System.out.println(query1);
					sql.printStackTrace();
				}
			}}, 0, 10000);
			}
	 
	public void receiveChallenge() // / tegenstander
	{
		 /// dit code wordt altijd geactiveerd.   om de 10 secondes
		//activatie index 1
		ResultSet dbResult;
		final int sspelid = spelid;
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				int index = 0;
				
					String query = "SELECT `Account_naam_tegenstander ` FROM `Spel`; where(INT ID) values(index);"; //??
					String query2 = "SELECT `spel id ` FROM `Spel`; where(INT ID) values(index);";
					
					//voorkomt nog een uitnodiging van de zelfde persoon
				 
					try {
						while (result.next())// of meer wiv
						{
						try {
							ResultSet dbResult =  new Query(query).select();
							ResultSet dbResult2 = new Query("SELECT `Reaktie ` FROM `Spel`; where(INT ID) values(index);").select();
							if(dbResult2.getString(index).equals(STATE_UNKNOWN) ||dbResult2.getString(index).equals(STATE_ACCEPTED) )
							{
								index++;
							}
							if (dbResult.getString(index).equals("getplayer name-_- )"))  // ///											// ????
							{
								// array 
								challengename.add(dbResult.getString(index));
								commandsToChallengeview("1");
								new Query("INSERT INTO `Spel` (`Reaktie`) values ( '"+STATE_UNKNOWN+ "' ).exec();");
								 
								//challengegamenumber.add(index);
							}
							dbResult =  new Query(query2).select();
							if (dbResult.getString(index)  == "getplayer name-_-") // ///											// ????
							{
								// array 
								challengegamenumber.add(dbResult.getInt(index));
								 
								//challengegamenumber.add(index);
							}
						} catch (SQLException sql) {
							System.out.println(query);
							sql.printStackTrace();
						}
						index++;
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}}, 0, 10000); 
			String query = "SELECT `Account_naam_tegenstander ` FROM `Spel`; where(INT ID) values(index);";
			}
	 
	public void acceptCallenge(int id) // uitdgedaagde
	{
		String query = "DELETE FROM `Spel` (`Toestand_type`,Reaktie), where(ID INT) VALUES ('" + id
				+ "') ;"; 
		String query2 = "INSERT INTO `Spel` (`Toestand_type`,Reaktie) VALUES ('"
				+ STATE_PLAYING +  STATE_ACCEPTED+ " ') where(ID INT) VALUES ('" + spelid
				+ "') ;";  
		try {
			new Query(query).exec();
			new Query(query2).exec();
			///
		 //spele
		} catch (SQLException sql) {
			System.out.println(query2);
			sql.printStackTrace();
		}
	}
	public void declineCallenge() // uitdgedaagde
	{
		String query = "INSERT INTO `Spel` (Reaktie) VALUES ('"+
				STATE_REJECTED+ " ') where(ID INT) VALUES ('" + spelid
				+ "') ;"; // insert or remove
		try {
			new Query(query);
		 //spele
		} catch (SQLException sql) {
			System.out.println(query);
			sql.printStackTrace();
		}
	}
	
	public void commandsToChallengeview(String commando)
	{	
		this.setChanged();
		this.notifyObservers(commando);
	}
//// die onder wordt niet echt gebruikt
	public void  OnlinePlayers(int competitionID) {		
		ArrayList<String> array = new ArrayList<String>();		
		String query = "SELECT `account_naam` FROM `deelnemer`;";
		try {
			ResultSet dbResult = new Query(query).select();
			array.add(dbResult.getString("account_naam"));		
		}
		catch (SQLException sql) 
		{			
			sql.printStackTrace();		
		}		
	}
	
}


/// teestz
