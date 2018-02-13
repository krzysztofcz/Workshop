package pl.coderslab.workshop.admin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.User;
import pl.coderslab.workshop.mysql.ConnectDB;
import pl.coderslab.workshop.tools.CheckEmailAddress;
public class AdminUser extends Admin{

	private static String name = "User";
	private static User obiekt = new User() ;
	private static User[] listaObiektow = new User[0];
	/**
	 * @return the name
	 */
	public static String getNazwa() {
		return name;
	}

	public static void main(String[] args) throws SQLException {
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
	 * Pokazuje liste wszystkich User z db
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
	 *  Dodaje User i pyta o dane 
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
		obiekt = obiektAdd(pola, wartosci);
		if (!new CheckEmailAddress().isValid(obiekt.getEmail())|| checkNull(obiekt.getUsername()) ||
			checkNull(obiekt.getPassword()) || checkNull(obiekt.getEmail())	) {
			System.out.println("All fields need to be fill corectly!");
		} else {
			System.out.println("All right.");
			try {
				Connection con = ConnectDB.connect();
				obiekt.saveToDB(con);
				showAll();
				System.out.println("Added "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 *  Sluzy do dodania przygotowania obiektu do zapisu, 
	 *  set ( ustawia ) pola o danych wartosciach
	 *  tablice pobrane od uzytkownika  
	 * @param pola - tabli stringow z nazwami pol, 
	 * @param wartosci - tablica stringow z wartoscami pol
	 * @return zwraca obiekt gotowy do zapisania
	 */
	private static User obiektAdd(String[] pola,String[] wartosci) { 
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
	
	/** edytuje usera o wybranym id ktory pobiera ze scanera
	 * @param scan 
	 */
	private static void edit(Scanner scan) {
		try {
			Connection con = ConnectDB.connect();
			showAll();
			System.out.println("Give me "+getNazwa()+" ID for EDITion:");
			int id = scan.nextInt();
			User user = User.loadById(con, id);
			if(user !=null) {
				System.out.println("=== START of editing "+getNazwa()+"===");
				System.out.println("ID: "+user.getId()+"(pole chronione).");
				scan.nextLine();//MUSAILEM DODAC TA LINIE bo mi przeskakiwal scan z emaila od razu na username
				System.out.println("Current email: "+user.getEmail()+" .\nType new email : ");
				user.setEmail(scan.nextLine());
				System.out.println("Current username: "+user.getUsername()+" .\nType new username : ");
				user.setUsername(scan.nextLine());
				System.out.println("Current password: "+user.getPassword()+" .\nType new password : ");
				user.setPassword(scan.nextLine());
				System.out.println("=== END of editing "+getNazwa()+"===");
				
				System.out.println("!!! Are you sure you wanna save it ??? \n (1) Y - Yes. \n"
						+ " for NO : (0)  type enything else than \"y\" or \"yes\"");
				String areUsure=scan.nextLine();
				boolean areYouSure=areUsure.equalsIgnoreCase("Yes")||areUsure.equalsIgnoreCase("Y")||areUsure.equalsIgnoreCase("1");
				
				if(areYouSure){
					user.saveToDB(con);
					System.out.println("New value for "+getNazwa()+" \""+ user.getUsername()+"\"");
					System.out.println(user);
				} else {
					System.out.println("I didnt save those changes. Try again.");
				}
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 * usuwa usera o podanym ID , jesli takie id istnieje 
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
				System.out.println("\nDeleted "+getNazwa()+" : "+obiekt.toString("pola+wartosci"));
				System.out.println(getNazwa()+" with ID: "+id+" DELETED from list.\n");
				obiekt.delete(con);
			};
			showAll();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
