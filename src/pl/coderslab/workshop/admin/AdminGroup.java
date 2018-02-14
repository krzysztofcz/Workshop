package pl.coderslab.workshop.admin;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.Group;
public class AdminGroup extends Admin {

	private static Group obiekt = new Group();
	private static String nazwa = obiekt.getClass().getSimpleName();

	/**
	 * @return the name
	 */
	public static String getNazwa() {
		return nazwa;
	}
     
	public static void main(String[] args) throws 	SQLException, ClassNotFoundException, NoSuchMethodException, 
													SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{		
		showAllv2(obiekt);
		PrintOutMenu menu = new PrintOutMenu(nazwa);
		Scanner scan = new Scanner(System.in);		
		String command = null;
		boolean quitOr4=true;
		while( quitOr4 )  {
			System.out.println(menu);
			command=scan.nextLine();
			
			//musailem do dodac aby sprawdzic czy jest true or not bo mi sie zaczelo motac strasznie i nie dzialalo poprawnie to se sprwdzilem warunek
			quitOr4=!(command.equalsIgnoreCase("QUIT")||command.equalsIgnoreCase("4")); 
			
			if(command.equalsIgnoreCase("add")||command.equalsIgnoreCase("1")) {
				showAllv2(obiekt);
				addv2(scan,obiekt);
				showAllv2(obiekt);
			}
			if(command.equalsIgnoreCase("edit")||command.equalsIgnoreCase("2")) {
				showAllv2(obiekt);
				editv2(scan,obiekt);
				showAllv2(obiekt);
			}
			if(command.equalsIgnoreCase("delete")||command.equalsIgnoreCase("3")) {
				showAllv2(obiekt);
				deletev2(scan,obiekt);
				showAllv2(obiekt);
			}
		}

		scan.close();
	}
}

//	/** CHYBA UNIWERSAL 
//	 * edytuje Exercise o wybranym id ktory pobiera ze scanera
//	 * @param scan 
//	 * @throws SQLException 
//	 */
//	private static void edit(Scanner scan) throws SQLException {
//		System.out.println("Give me "+getNazwa()+" ID for EDITion:");
//		int id = scan.nextInt();
//		obiekt = obiekt.loadById(id);
//		if(obiekt !=null) {
//			System.out.println("That your choise : "+obiekt.toString("pola+wartosci"));
//			String[] pola = obiekt.toString("req+pola").split(",");
//			String[] wartosci = obiekt.toString("req+wartosci").split(",");
//			String[] noweWartosci = new String[pola.length];
//			System.out.println("=== START of editing "+getNazwa()+"===");
//			scan.nextLine();//MUSAILEM DODAC TA LINIE bo mi przeskakiwal scan.nextLine() łapał auto entera
//
//			for(int i=0;i<pola.length;i++) {
//				System.out.println("Current "+pola[i]+" value is : "+wartosci[i]+".\nType new : "+pola[i]);
//				noweWartosci[i]=scan.nextLine();
//			}
//			System.out.println("=== END of editing "+getNazwa()+"===");
//			System.out.println("!!! Are you sure you wanna save it ??? \n (1) Y - Yes. \n"
//					+ " (0) \"zero\" or press ANY KEY for NO=DONT SAVE=CANCEL");
////			String areUsure=scan.nextLine();
//			
//			if(areYouSure(scan)){
//				obiekt=obiektAdd(pola,noweWartosci);
//				obiekt.saveToDB();
//				System.out.println("New value for "+getNazwa()+" with ID : "+ obiekt.getId()  +" is : \n");
//				System.out.println(obiekt);
//			} else {
//				System.out.println("NO: nothing to save. You can try again.");
//			}
//		} else {
//			System.out.println("This ID : "+id+" is invalid. \n");
//			scan.nextLine();//ADDED COS IT WAS PRINTIG 2 TIME showAll method
//		}
//	}
	
//
//	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
//	 * Pokazuje liste wszystkich zadan z db
//	 * @throws SQLException 
//	 * @throws ClassNotFoundException 
//	 */
//	public static void showAll() throws SQLException {
//		System.out.println("===START LIST of "+getNazwa()+": ===");
//		listaObiektow = obiekt.loadAll(); 
//		for (int i = 0; i < listaObiektow.length; i++) {
//			obiekt=listaObiektow[i]; 						
//			System.out.println(obiekt);
//		}
//		obiekt.clear();
//		System.out.println("===END LIST of "+getNazwa()+"=== \n");
//	}
	
//	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE 
//	 *  Dodaje zadanie i pyta o dane 
//	 *  i na koniec zapisuje do bazy danych
//	 * @param scan
//	 * @throws SQLException
//	 */
//	public static void add(Scanner scan) throws SQLException {
//		System.out.println("Type all values for "+getNazwa()+" :");
//		String[] pola = obiekt.toString("req+pola").split(",");
//		String[] wartosci = new String[pola.length];
//		for(int i=0;i<pola.length;i++) {
//			System.out.println(pola[i]);
//			wartosci[i]=scan.nextLine();			
//		}
//		obiekt.clear();
//		obiekt=obiektAdd(pola,wartosci);
//		obiekt.saveToDB();
//		System.out.println("Added "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
//	}
	
//	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
//	 *  Sluzy do dodania przygotowania obiektu do zapisu, 
//	 *  set ( ustawia ) pola o danych wartosciach
//	 *  tablice pobrane od uzytkownika  
//	 * @param pola - tabli stringow z nazwami pol, 
//	 * @param wartosci - tablica stringow z wartoscami pol
//	 * @return zwraca obiekt gotowy do zapisania
//	 */
//	private static Group obiektAdd(String[] pola,String[] wartosci) {
//		for(int i=0;i<pola.length;i++) {
//			try {
//			    Class klasa = obiekt.getClass();
//				Class[] klasaArgumentow = {wartosci[i].getClass()};
//			    Method mojaMetodaZKlasy = klasa.getMethod("set"+pola[i], klasaArgumentow);
//				mojaMetodaZKlasy.invoke(obiekt,wartosci[i]);
//				
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			}
//		}
//		return obiekt;
//	}



//	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
//	 * usuwa wpis o podanym ID , jesli takie id istnieje 
//	 * @param scan
//	 * @throws SQLException 
//	 * @throws ClassNotFoundException 
//	 */
//	public static void delete(Scanner scan) throws SQLException, ClassNotFoundException {
//		showAllv2(nazwa);
//		System.out.println("Choose ID from "+getNazwa()+"'s list to DELETE :");
//		int id = scan.nextInt();
//		scan.nextLine();
//		obiekt = obiekt.loadById(id);
//		if(obiekt!=null) {
//			System.out.println("\nDeleted "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
//			obiekt.delete();
//			System.out.println(getNazwa()+" with ID: "+id+" DELETED from list.\n");
//		} else {
//			System.out.println("This ID : "+id+" is invalid. \n");
//		};
//
//	}
