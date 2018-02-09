package pl.coderslab.workshop.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.models.User;

public class main1 {

	public static void main(String[] args) {
		
		String url="jdbc:mysql://127.0.0.1:3306/workshop";
		String user="root";
		String password="coderslab";
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
//	    	User nowyUser = new User();
//	    	nowyUser.saveToDB(con);
	    	User nowyUser1 = new User("imie i login","email@w2p.pl","moj4ehaslo");
	    	nowyUser1.saveToDB(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(url, user, password)){
	    	System.out.println(User.loadUserById(con, 0));
	    	System.out.println(User.loadUserById(con, 1));
	    	System.out.println(User.loadUserById(con, 2));
	    	User test=User.loadUserById(con, 2);
	    	test.setEmail("blabla@blabla.pl");
	    	test.saveToDB(con);
	    	System.out.println(User.loadUserById(con, 2));
	    	System.out.println(User.loadUserById(con, 3));
	    	User[] czeckall = User.loadAllUsers(con);
	    	
	    	
	    			for (User jeden : czeckall ) {
	    				System.out.println(jeden.getUsername()+" "+jeden.getEmail()+" "+jeden.getPassword());
	    			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
