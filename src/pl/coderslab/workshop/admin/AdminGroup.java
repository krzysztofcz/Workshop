package pl.coderslab.workshop.admin;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.Exercise;
import pl.coderslab.workshop.mysql.ConnectDB;
import pl.coderslab.workshop.models.Group;
public class AdminGroup {

	private static String nazwa = "Group";
	private static Group obiekt = new Group();
	private static Group[] listaObiektow = new Group[0];
	
	/**
	 * @return the name
	 */
	public static String getNazwa() {
		return nazwa;
	}
     
	public static void main(String[] args) throws SQLException{		
		showAll();
		PrintOutMenu menu = new PrintOutMenu(getNazwa());
		Scanner scan = new Scanner(System.in);		
		String command = "";
		boolean quitOr4=true;
		while( quitOr4 )  {
			System.out.println(menu);
			command=scan.nextLine();
			
			//musailem do dodac aby sprawdzic czy jest true or not bo mi sie zaczelo motac strasznie i nie dzialalo poprawnie to se sprwdzilem warunek
			quitOr4=!(command.equalsIgnoreCase("QUIT")||command.equalsIgnoreCase("4")); 
			
			if(command.equalsIgnoreCase("add")||command.equalsIgnoreCase("1")) {
				add(scan);
			}
			if(command.equalsIgnoreCase("edit")||command.equalsIgnoreCase("2")) {
				edit(scan);
			}
			if(command.equalsIgnoreCase("delete")||command.equalsIgnoreCase("3")) {
				delete(scan);
			}
		}

		scan.close();
	}

	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 * Pokazuje liste wszystkich zadan z db
	 * @throws ClassNotFoundException 
	 */
	public static void showAll() {
		System.out.println("===START LIST of "+getNazwa()+": ===");
		try {
			Connection con = ConnectDB.connect(); 
			listaObiektow = obiekt.loadAll(con); 
			for (int i = 0; i < listaObiektow.length; i++) {
				obiekt=listaObiektow[i]; 						
				System.out.println(obiekt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obiekt.clear();
		System.out.println("===END LIST of "+getNazwa()+"=== \n");
	}
	
	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE 
	 *  Dodaje zadanie i pyta o dane 
	 *  i na koniec zapisuje do bazy danych
	 * @param scan
	 * @throws SQLException
	 */
	public static void add(Scanner scan) {
		System.out.println("Type all values for "+getNazwa()+" :");
		String[] pola = obiekt.toString("req+pola").split(",");
		String[] wartosci = new String[pola.length];
		for(int i=0;i<pola.length;i++) {
			System.out.println(pola[i]);
			wartosci[i]=scan.nextLine();			
		}
		obiekt.clear();
		obiekt = obiektAdd(pola,wartosci);
		try {
			Connection con = ConnectDB.connect();
			obiekt.saveToDB(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		showAll();
		System.out.println("Added "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
	}
	
	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 *  Sluzy do dodania przygotowania obiektu do zapisu, 
	 *  set ( ustawia ) pola o danych wartosciach
	 *  tablice pobrane od uzytkownika  
	 * @param pola - tabli stringow z nazwami pol, 
	 * @param wartosci - tablica stringow z wartoscami pol
	 * @return zwraca obiekt gotowy do zapisania
	 */
	private static Group obiektAdd(String[] pola,String[] wartosci) {
		for(int i=0;i<pola.length;i++) {
			try {
			    Class klasa = obiekt.getClass();
//				Class[] klasaArgumentow = new Class[1];
//		        klasaArgumentow[0] = wartosci[i].getClass();
				Class[] klasaArgumentow = {wartosci[i].getClass()};
			    Method mojaMetodaZKlasy = klasa.getMethod("set"+pola[i], klasaArgumentow);
				mojaMetodaZKlasy.invoke(obiekt,wartosci[i]);
				
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return obiekt;
	}

	/** CHYBA UNIWERSAL 
	 * edytuje Exercise o wybranym id ktory pobiera ze scanera
	 * @param scan 
	 */
	private static void edit(Scanner scan) {
		try {
			Connection con = ConnectDB.connect();
			showAll();
			System.out.println("Give me "+getNazwa()+" ID for EDITion:");
			int id = scan.nextInt();
			obiekt = obiekt.loadById(con, id);
			if(obiekt !=null) {
				System.out.println("That your choise : "+obiekt.toString("pola+wartosci"));
				String[] pola = obiekt.toString("req+pola").split(",");
				String[] wartosci = obiekt.toString("req+wartosci").split(",");
				String[] noweWartosci = new String[pola.length];
				System.out.println("=== START of editing "+getNazwa()+"===");
				scan.nextLine();//MUSAILEM DODAC TA LINIE bo mi przeskakiwal scan.nextLine() łapał auto entera

				for(int i=0;i<pola.length;i++) {
					System.out.println("Current "+pola[i]+" value is : "+wartosci[i]+".\nType new : "+pola[i]);
					noweWartosci[i]=scan.nextLine();
				}
				
				System.out.println("=== END of editing "+getNazwa()+"===");
				
				System.out.println("!!! Are you sure you wanna save it ??? \n (1) Y - Yes. \n"
						+ " (0) \"zero\" or press ANY KEY for NO=DONT SAVE=CANCEL");
				String areUsure=scan.nextLine();
				boolean areYouSure=areUsure.equalsIgnoreCase("Yes")||areUsure.equalsIgnoreCase("Y")||areUsure.equalsIgnoreCase("1");
				if(areYouSure){
					obiekt=obiektAdd(pola,noweWartosci);
					obiekt.saveToDB(con);
					System.out.println("New value for "+getNazwa()+" with ID : "+ obiekt.getId()  +" is : \n");
					System.out.println(obiekt);
				} else {
					System.out.println("NO: nothing to save. You can try again.");
				}
			} else {
				System.out.println("This ID : "+id+" is invalid. \n");
				scan.nextLine();//ADDED COS IT WAS PRINTIG 2 TIME showAll method
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 * usuwa zadanie o podanym ID , jesli takie id istnieje 
	 * @param scan
	 */
	public static void delete(Scanner scan) {
		try {
			Connection con = ConnectDB.connect();
			showAll();
			System.out.println("Choose ID from "+getNazwa()+"'s list to DELETE :");
			int id = scan.nextInt();
			scan.nextLine();
			obiekt = obiekt.loadById(con, id);
			if(obiekt!=null) {
				String temp=("\nDeleted "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
				obiekt.delete(con);
				showAll();
				System.out.println(temp);
				System.out.println(getNazwa()+" with ID: "+id+" DELETED from list.\n");
			} else {
				System.out.println("This ID : "+id+" is invalid. \n");
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}