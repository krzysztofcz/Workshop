package pl.coderslab.workshop.admin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Scanner;

import pl.coderslab.workshop.models.Exercise;
import pl.coderslab.workshop.models.Solution;
import pl.coderslab.workshop.models.User;

public class AdminSolution extends Admin{

	private static Solution obiekt = new Solution();
	private static String nazwa = obiekt.getClass().getSimpleName();
	
	/**
	 * @return the name
	 */
	public static String getNazwa() {
		return nazwa;
	}
     
	public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, SecurityException, 
												  IllegalAccessException, IllegalArgumentException, InvocationTargetException{		
		showAllv2(obiekt);
		PrintOutMenu menu = new PrintOutMenu(nazwa);
		Scanner scan = new Scanner(System.in);		
		String command = null;
		boolean quitOr4=true;
		while( quitOr4 )  {
			System.out.println(menu.toString(obiekt));
			command=scan.nextLine();
			
			//musailem do dodac aby sprawdzic czy jest true or not bo mi sie zaczelo motac strasznie i nie dzialalo poprawnie to se sprwdzilem warunek
			quitOr4=!(command.equalsIgnoreCase("QUIT")||command.equalsIgnoreCase("4")); 
			
			if(command.equalsIgnoreCase("add")||command.equalsIgnoreCase("1")) {
				showAllv2(obiekt);
				addv2(scan,obiekt);
				showAllv2(obiekt);
			}
			if(command.equalsIgnoreCase("viw")||command.equalsIgnoreCase("2")) {
				showAllv2(obiekt);
				viewv2(scan,obiekt);
				showAllv2(obiekt);
			}
		}
		scan.close();
	}	
	
}
