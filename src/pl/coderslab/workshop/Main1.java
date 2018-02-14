package pl.coderslab.workshop;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;
import pl.coderslab.workshop.admin.Admin;
import pl.coderslab.workshop.admin.PrintOutMenu;
import pl.coderslab.workshop.models.Solution;
import pl.coderslab.workshop.models.User;

public class Main1 {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, SecurityException, 
													IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Integer id = Integer.parseInt(args[0].split("=")[1]);	
		User user= User.loadById(id);
		Solution solution = new Solution();
		PrintOutMenu stringMenu = new PrintOutMenu("");
		String rightStrinMenu = stringMenu.toString(solution);
		String command = null;
		boolean quitOr4=true;
		Scanner scan = new Scanner(System.in);
		while( quitOr4 )  {
			System.out.print(rightStrinMenu);
			command=scan.nextLine();
//			
//			//musailem do dodac aby sprawdzic czy jest true or not bo mi sie zaczelo motac strasznie i nie dzialalo poprawnie to se sprwdzilem warunek
			quitOr4=!(command.equalsIgnoreCase("QUIT")||command.equalsIgnoreCase("4")); 
//			
			if(command.equalsIgnoreCase("add")||command.equalsIgnoreCase("1")) {
				Solution[] sol=Admin.viewv3(solution);
				System.out.println("Exercises without Solution for User :\n"+user+"\n");
				if(sol!=null) {
					for(Solution s : sol) {
						if(s.getUsers_id()==user.getId() && s.getDescription()==null) {
							System.out.println(s);
						}
					}
				}
				System.out.println("\nChoose ID to update a Solution:\n");
				id=scan.nextInt();
				command = scan.nextLine();
				solution = Solution.loadById(id);
				if(solution!=null) {
					System.out.println("\nYour Solution is empty now , update id \n");
					command = scan.nextLine();
					if(command!=null && !command.equalsIgnoreCase("")) {
						solution.setDescription(command);
						solution.saveToDB();
					} else {
						System.out.println("\nEmpty DESCRIPTION in solution not allowed ! \n");
					}
				} else {
					System.out.println("\nID is wrong !\n");
				}
				
//				Admin.viewv2(scan,solution,user,notNull);
//				Admin.addv2(scan,solution,user);
			}
			if(command.equalsIgnoreCase("edit")||command.equalsIgnoreCase("2")) {
//				Admin.viewv2(scan,solution,user);
				Solution[] sol=Admin.viewv3(solution);
				System.out.println("For user :\n"+user+"\n");
				if(sol!=null) {
					for(Solution s : sol) {
						if(s.getUsers_id()==user.getId()) {
							System.out.println(s);
						}
					}
				}
			}

		}

		scan.close();
	}

}

