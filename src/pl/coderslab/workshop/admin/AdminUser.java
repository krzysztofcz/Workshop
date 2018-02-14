package pl.coderslab.workshop.admin;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.User;
public class AdminUser extends Admin {

	private static User obiekt = new User();
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