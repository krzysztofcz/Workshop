package pl.coderslab.workshop.admin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.Exercise;
import pl.coderslab.workshop.models.Solution;
import pl.coderslab.workshop.models.User;

public class Admin {
	
	/**zwraca true of false jesli zmienna(tutaj string) jest pusta 
	 * @param string - Zmienna napis
	 * @return
	 */
	public static boolean checkNull(String str){
		return str.equals(null); 
	}
	
	/** Zwroc liste obiektow o podanej nazwie 
	 * @param obj - obiekt typu : Exercise, Group, Solution lub User
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void showAllv2(Object obj) throws SQLException,ClassNotFoundException, NoSuchMethodException, SecurityException, 
													IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("===START LIST of "+obj.getClass().getSimpleName()+": ===v2");
				Class<?> klasa = obj.getClass();
				Object obiekt = klasa.getClass();				
				Object[] listaObiektow;
				Class<?>[] argListNull = null;
				Method methodLoadAll = klasa.getMethod("loadAll", argListNull);
				listaObiektow = (Object[]) methodLoadAll.invoke(obiekt, argListNull);
				if(listaObiektow!=null) {
					for (int i = 0; i < listaObiektow.length; i++) {
						obiekt=listaObiektow[i]; 						
						System.out.println(obiekt);
					}
				}
		System.out.println("===END LIST of "+obj.getClass().getSimpleName()+" ===v2 \n");
	}
	
	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE
	 * usuwa wpis o podanym ID , jesli takie id istnieje 
	 * @param scan
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void deletev2(Scanner scan,Object obj) throws SQLException, ClassNotFoundException, NoSuchMethodException, SecurityException, 
																IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String String = obj.getClass().getSimpleName();
		System.out.println("Choose ID from "+String+"'s list to DELETE :");
		Integer id = scan.nextInt();
		scan.nextLine();
		Class<?> klasa = obj.getClass();
//		System.out.println(obj.getClass());
		Object obiekt = obj.getClass().getName();
//		System.out.println(obj.getClass().getName());
		Class<?>[] argList4methodLoadById= { id.getClass() };
		Method methodLoadById = klasa.getMethod("loadById", argList4methodLoadById);
		obiekt = methodLoadById.invoke(obiekt, id);
		if(obiekt!=null) {
			Class<?>[] argList4methodToString = {String.class};
			Method methodToString = klasa.getMethod("toString", argList4methodToString );
			System.out.println("\nDeleted "+String+" : "+methodToString.invoke(obiekt,"pola+wartosci"));
			Class<?>[] argList4methodDelete = null;
			Method methodDelete = klasa.getDeclaredMethod("delete", argList4methodDelete);
//			System.out.println(methodDelete.getName());
			methodDelete.invoke(obiekt, null);
			System.out.println(String+" with ID: "+id+" DELETED from list.\n");
		} else {
			System.out.println("This ID : "+id+" is invalid. \n");
		};

	}
	
	public static boolean areYouSure(Scanner scan) {
		String areUsure=scan.nextLine();
		return (areUsure.equalsIgnoreCase("Yes")||areUsure.equalsIgnoreCase("Y")||areUsure.equalsIgnoreCase("1"));
	}
	
	/** UNIWERSALNE UNIWERSALNE UNIWERSALNE 
	 *  Dodaje nowy rekord i pyta o dane 
	 *  i na koniec zapisuje do bazy danych
	 * @param scan
	 * @throws SQLException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void addv2(Scanner scan,Object obj) throws SQLException, IllegalAccessException, IllegalArgumentException, 
															InvocationTargetException, NoSuchMethodException, SecurityException {
		String nazwa = obj.getClass().getSimpleName();
		System.out.println("Type all values for "+nazwa+" :");
		Class<?> klasa = obj.getClass();
		Object obiekt = obj.getClass().getName();
		Class<?>[] argList4toString = {String.class};
		Method metodaToString = klasa.getDeclaredMethod("toString", argList4toString);
		Object pole = metodaToString.invoke(obj,"req+pola");
		String[] pola = pole.toString().split(",");
		String[] wartosci = new String[pola.length];
		String email=null,username=null,password=null;
		for(int i=0;i<pola.length;i++) {
			System.out.println(pola[i]);
			wartosci[i]=scan.nextLine();
			if(obj.getClass().getSimpleName().equalsIgnoreCase("User")) {
				if(wartosci[i]==null||wartosci[i].equals("")) {
					if(pola[i].equalsIgnoreCase("username")) {
						username=wartosci[i];
					}
					if(pola[i].equalsIgnoreCase("password")) {
						password=wartosci[i];
					}
				}
			}
		}
		Class<?>[] argNull = null;
		Method metodaClear = klasa.getDeclaredMethod("clear", argNull);
		metodaClear.invoke(obj, null);
		obiekt=obiektAdd(obj,pola,wartosci);
		Method metodaSaveToDB = klasa.getDeclaredMethod("saveToDB", argNull);
		metodaToString = klasa.getDeclaredMethod("toString", argList4toString);
		Object getUserByEmail=null;
		boolean allRight = false;
		if(obj.getClass().getSimpleName().equalsIgnoreCase("USER")) {
			Method metodaGetEmail = klasa.getDeclaredMethod("getEmail", argNull);
			Method metodaEmailUniqu = klasa.getDeclaredMethod("emailIsUnique", argList4toString);
			email = (String) metodaGetEmail.invoke(obiekt, null);
			getUserByEmail = metodaEmailUniqu.invoke(obiekt, email);
			if(!(getUserByEmail==null||username.equalsIgnoreCase("")||password.equalsIgnoreCase(""))) {
				metodaSaveToDB.invoke(obiekt,null);
				getUserByEmail = metodaEmailUniqu.invoke(obiekt, email);
				allRight=true;
			} else {
				System.out.println("All fields need to be fill corectly!");
			}
		}
		if(!obj.getClass().getSimpleName().equalsIgnoreCase("USER")) {
			metodaSaveToDB.invoke(obiekt, null);
			allRight=true;
		}
		if (allRight) {
			obiekt=getUserByEmail;
			System.out.println("All right.");
			String polaIWartosci = metodaToString.invoke(obiekt,"pola+wartosci").toString();
			System.out.println("Added "+nazwa+" : "+polaIWartosci);
		}
	}
	
	/** NOT UNIVERSAL 
	 * Just for Solution class ,add new solution
	 * @param scan - scanner 
	 * @param obj - object type of solution

	 */
	public static void addv2(Scanner scan,Solution obj) throws SQLException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		User user=new User();
		Exercise exercise=new Exercise();
		showAllv2(user);
		System.out.println("Please choose a 'User ID' you wanna assign for Exercise : ");
		Integer user_id = scan.nextInt();

		showAllv2(exercise);	
		System.out.println("Please choose a 'Exercise ID' you wanna assign for User : ");
		Integer exercise_id = scan.nextInt();
		
		user=User.loadById(user_id);
		exercise=Exercise.loadById(exercise_id);
		Solution[] allSolutions=Solution.loadAll();
		boolean solutionExist=false;
		for( Solution sol : allSolutions) {
			if(	(sol.getExercise_id()==exercise_id) && (sol.getUsers_id() == user_id) ) {
				solutionExist=true;
			}
		}
		if(solutionExist) {
			System.out.print("Solution already exist.\n");
		} else if ( !(user==null || exercise==null) ) {
			obj.clear();
			obj.setExercise_id(exercise_id);
			obj.setUsers_id(user_id);
			obj.saveToDB();
			System.out.println("All right.\n");
		} else {
			System.out.println("ID is wrong, User or Exercise doesn't exist.\n");
		}
		scan.nextLine();
	}
	
	/** NOT UNIVERSAL
	 *  Display all solutions by User ID 
	 * @param scan
	 * @param solution
	 */
	public static void viewv2(Scanner scan,Solution solution) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, 
																		IllegalArgumentException, InvocationTargetException, SQLException {
		User user = new User();
		showAllv2(user);
		System.out.println("Please choose a 'User ID' you wanna view Exercises : \n");
		Integer id = scan.nextInt();
		scan.nextLine();
		user=User.loadById(id);
		if(user!=null) {
			Solution[] solByUserId=Solution.loadAllByUsersId(user);
			System.out.println("All solution for user : "+user+"\n");
			for(Solution sol : solByUserId) {
				System.out.println(sol);
			}
			System.out.println("");
		} else {
			System.out.println("User ID is wrong ! .\n");
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
	public static Object obiektAdd(Object obj,String[] pola,String[] wartosci) throws NoSuchMethodException, SecurityException, IllegalAccessException, 
																						IllegalArgumentException, InvocationTargetException {
		for(int i=0;i<pola.length;i++) {
			    Class<?> klasa = obj.getClass();
				Class<?>[] klasaArgumentow = {wartosci[i].getClass()};
			    Method mojaMetodaZKlasy = klasa.getMethod("set"+pola[i], klasaArgumentow);
				mojaMetodaZKlasy.invoke(obj,wartosci[i]);
		}
		return obj;
	}
	
	/** CHYBA UNIWERSAL DLA USER , GROUP, EXERCISES classes
	 * edytuje wybrany rekord o wybranym id ktory pobiera ze scanera
	 * @param scan 
	 * @throws SQLException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void editv2(Scanner scan,Object obj) throws SQLException, NoSuchMethodException, SecurityException, IllegalAccessException, 
																IllegalArgumentException, InvocationTargetException {
		String nazwa = obj.getClass().getSimpleName();
		System.out.println("Give me "+nazwa+" ID for EDITion:");
		Integer id = scan.nextInt();
		Class<?> klasa = obj.getClass();
		Object obiekt = obj.getClass();
		Class<?>[] arg4LoadById = { Integer.class } ;
		Method metodaLoadById = klasa.getMethod("loadById", arg4LoadById );
		obiekt = metodaLoadById.invoke(obj, id);
		if(obiekt !=null) {
			Class<?>[] argList4toString = {String.class};
			Method metodaToString = klasa.getMethod("toString", argList4toString);
 
			String polaIWartosci = metodaToString.invoke(obiekt,"pola+wartosci").toString();
			String reqPola = metodaToString.invoke(obiekt,"req+pola").toString();
			String reqWartosci = metodaToString.invoke(obiekt,"req+wartosci").toString();
			System.out.println("That your choise : "+polaIWartosci);
			String[] pola = reqPola.split(",");
			String[] wartosci = reqWartosci.split(",");
			String[] noweWartosci = new String[pola.length];
			
			System.out.println("=== START of editing "+nazwa+"===");
			scan.nextLine();//MUSAILEM DODAC TA LINIE bo mi przeskakiwal scan.nextLine() łapał auto entera
			String username=null,password=null,email=null;
			for(int i=0;i<pola.length;i++) {
				System.out.println("Current "+pola[i]+" value is "+wartosci[i]+".\nType new "+pola[i]+" : ");
				noweWartosci[i]=scan.nextLine();
				if(obj.getClass().getSimpleName().equalsIgnoreCase("User")) {
					if(wartosci[i]==null||wartosci[i].equals("")) {
						if(pola[i].equalsIgnoreCase("username")) {
							username=wartosci[i];
						}
						if(pola[i].equalsIgnoreCase("password")) {
							password=wartosci[i];
						}
					}
				}
			}
			System.out.println("=== END of editing "+nazwa+"===");
			System.out.println("!!! Are you sure you wanna save it ??? \n (1) Y - Yes. \n"
					+ " (0) \"zero\" or press ANY KEY for NO=DONT SAVE=CANCEL");
			if(areYouSure(scan)){
				obiekt=obiektAdd(obiekt,pola,noweWartosci);
				System.out.println(obiekt);
				Class<?>[] argNull = null;
				Object getUserByEmail=null;
				Method metodaSaveToDB = klasa.getDeclaredMethod("saveToDB", argNull);
				boolean allRight=true;
				if(obj.getClass().getSimpleName().equalsIgnoreCase("USER")) {
					Method metodaGetEmail = klasa.getDeclaredMethod("getEmail", argNull);
					Method metodaEmailUniqu = klasa.getDeclaredMethod("emailIsUnique", argList4toString);
					email = (String) metodaGetEmail.invoke(obiekt, null);
					getUserByEmail = metodaEmailUniqu.invoke(obiekt, email);
					if(!(getUserByEmail==null||username.equalsIgnoreCase("")||password.equalsIgnoreCase(""))) {
						metodaSaveToDB.invoke(obiekt,null);
						getUserByEmail = metodaEmailUniqu.invoke(obiekt, email);
						allRight=true;
					} else {
						System.out.println("All fields need to be fill corectly!");
					}
				}
				if(!obj.getClass().getSimpleName().equalsIgnoreCase("USER")) {
					metodaSaveToDB.invoke(obiekt, null);
					allRight=true;
				}
				if (allRight) {
					obiekt=getUserByEmail;
					metodaSaveToDB.invoke(obiekt, null);
					System.out.println("New value for "+nazwa+" with ID : "+ id  +" is : \n");
					System.out.println(obiekt);
				}
			} else {
				System.out.println("NO: nothing to save. You can try again.");
			}
		} else {
			System.out.println("This ID : "+id+" is invalid. \n");
			scan.nextLine();//ADDED COS IT WAS PRINTIG 2 TIME showAll method
		}
	}
}