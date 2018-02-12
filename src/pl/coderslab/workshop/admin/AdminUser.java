package pl.coderslab.workshop.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.User;
import pl.coderslab.workshop.mysql.ConnectDB;
import pl.coderslab.workshop.tools.CheckEmailAddress;
public class UsersAdmin {

	private String name = "użytkownia";
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public static void main(String[] args) throws SQLException {
		startShowAll();

		PrintOutMenu menu = new PrintOutMenu("użytkownika");
		Scanner scan = new Scanner(System.in);		
		String command = "";
		boolean quitOr4=true;
		while( quitOr4 )  {
			System.out.println(menu);
//			new PrintOutMenu("użytkownika");
			command=scan.nextLine();
			
			//musailem do dodac aby sprawdzic czy jest true or not bo mi sie zaczelo motac strasznie i nie dzialalo poprawnie to se sprwdzilem warunek
			quitOr4=!(command.equalsIgnoreCase("QUIT")||command.equalsIgnoreCase("4")); 
			
			if(command.equalsIgnoreCase("add")||command.equalsIgnoreCase("1")) {
				addUser(scan);
			}
			if(command.equalsIgnoreCase("edit")||command.equalsIgnoreCase("2")) {
				editUser(scan);
			}
			if(command.equalsIgnoreCase("delete")||command.equalsIgnoreCase("3")) {
				deleteUser(scan);
			}
		}

		scan.close();
	}

	/**
	 * Pokazuje liste wszystkich uzytkownikow z db
	 */
	public static void startShowAll() {
		System.out.println("===Oto Lista użytkowników : ===");
		try {
			Connection con = ConnectDB.connect();
			User[] users= User.loadAllUsers(con);
			for(User user:users) {
				System.out.println(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("===koniec listy użytkowników=== \n");
	}
	
	/** Dodaje uzytkownika i pyta o email, username, password 
	 *  i na koniec zapisuje do bazy danych
	 * @param scan
	 * @throws SQLException
	 */
	public static void addUser(Scanner scan) throws SQLException {
		System.out.println("Prosze podaj wartosci dla uzytkownia:");
		String[] pola = new User().toString("pola").split(",");
		String email=null,username=null,password=null;
		boolean polaSaNiePoprawne=true;
		
		while(polaSaNiePoprawne) {
			System.out.println(pola[1]);
			email = scan.nextLine();
			System.out.println(pola[2]);
			username = scan.nextLine();
			System.out.println(pola[3]);
			password = scan.nextLine();
						
			if( email.equals(null) || username.equals(null) || password.equals(null) || !email.contains("@") || !email.contains(".") ||
					!(new CheckEmailAddress().isValid(email)) ) {
				System.out.println("Wszystkie pola musza być wypelnione oraz być poprawne!");
			} else {
				polaSaNiePoprawne = false;
				System.out.println("Wszystkie pola są poprawnie wypelnione.");
			}
		}			
		try {
			Connection con = ConnectDB.connect();
			User user = new User(email,username,password);
			user.saveToDB(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		startShowAll();
	}
	
	/** edytuje usera o wybranym id ktory pobiera ze scanera
	 * @param scan 
	 */
	private static void editUser(Scanner scan) {
		try {
			Connection con = ConnectDB.connect();
			startShowAll();
			System.out.println("Pole podaj ID user do edycji:");
			int id = scan.nextInt();
			User user = User.loadUserById(con, id);
			if(user !=null) {
				System.out.println("=== POCZATEK EDYCJI ===");
				System.out.println("ID: "+user.getId()+"(pole chronione).");
				scan.nextLine();//MUSAILEM DODAC TA LINIE bo mi przeskakiwal scan z emaila od razu na username
				System.out.println("stary email: "+user.getEmail()+" .\nPodaj nowy email :");
				user.setEmail(scan.nextLine());
				System.out.println("start username: "+user.getUsername()+" .\nPodaj nowy username:");
				user.setUsername(scan.nextLine());
				System.out.println("stare haslo: "+user.getPassword()+" .\nPodaj nowy haslo :");
				user.setPassword(scan.nextLine());
				System.out.println("=== KONIEC EDYCJI ===");
				
				System.out.println("!!! Jestes pewny ze chcesz to zapisac ??? \n (1) T - Tak, Y - Yes. \n"
						+ " DLA OPCJI NIE: (0)  - cos innego niz  1/tak/t/y/yes ");
				String areUsure=scan.nextLine();
				boolean areYouSure=areUsure.equalsIgnoreCase("T")||areUsure.equalsIgnoreCase("TAK")||
						areUsure.equalsIgnoreCase("Yes")||areUsure.equalsIgnoreCase("Y");
				
				if(areYouSure){
					user.saveToDB(con);
					System.out.println("Oto nowe dane dla \""+ user.getUsername()+"\"");
					System.out.println(user);
				} else {
					System.out.println("Nic nie zeedytowano.");
				}
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** usuwa uzytkownika o podanym ID , jesli takie id istnieje
	 * @param scan
	 */
	public static void deleteUser(Scanner scan) {
		try {
			Connection con = ConnectDB.connect();
			System.out.println("Pole podaj ID user do kasacji:");
			startShowAll();
			int id = scan.nextInt();
			User user = User.loadUserById(con, id);
			if(user!=null) {
				user.delete(con);
			};
			System.out.println(user);
			startShowAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
