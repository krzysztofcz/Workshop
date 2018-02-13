package pl.coderslab.workshop;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.coderslab.workshop.models.Exercise;
import pl.coderslab.workshop.models.Group;
import pl.coderslab.workshop.models.Solution;
import pl.coderslab.workshop.models.User;
import pl.coderslab.workshop.mysql.ConnectDB;


public class main1 {

	public static void main(String[] args) throws SQLException {
			
		String url="jdbc:mysql://127.0.0.1:3306/workshop";
		String user="root";
		String password="coderslab";
		
//		try (Connection con = DriverManager.getConnection(url, user, password)){
//	    	System.out.println(User.loadUserById(con, 0));
//	    	System.out.println(User.loadUserById(con, 1));
//	    	System.out.println(User.loadUserById(con, 2));
//	    	User test=User.loadUserById(con, 2);
//	    	test.setEmail("blabla@blabla.pl");
//	    	test.saveToDB(con);
//	    	System.out.println(User.loadUserById(con, 2));
//	    	System.out.println(User.loadUserById(con, 3));
//	    	User[] czeckall = User.loadAllUsers(con);
//			
//	    			for (User jeden : czeckall ) {
//	    				System.out.println(jeden.getUsername()+" "+jeden.getEmail()+" "+jeden.getPassword());
//	    			}

//			Group grupa0 = new Group("grupa0 mysql and other DB");
//			Group grupa1 = new Group("grupa1 php");
//			Group grupa2 = new Group("grupa2 java");
//			Group grupa3 = new Group("grupa3 python");
//			Group grupa4 = new Group("grupa4 bash perl etc");
			
//			Group grupa0 = Group.loadGroupById(con, 1);
//			Group grupa1 = Group.loadGroupById(con, 2);
//			Group grupa2 = Group.loadGroupById(con, 3);
//			Group grupa3 = Group.loadGroupById(con, 4);
//			Group grupa4 = Group.loadGroupById(con, 5);
			
//			grupa0.setName("TESTERZY");
//			grupa1.setName("OOP");
//			grupa2.setName("MANULA TESTERZY");
//			grupa1.setName("OOP OOP");
//			grupa2.setName("MANULA AND AUTOMATIC TESTERS");
//			
//			grupa0.saveToDB(con);
//			grupa1.saveToDB(con);
//			grupa2.saveToDB(con);
//			grupa3.saveToDB(con);
//			grupa4.saveToDB(con);
			
//			Group grupa = new Group();
//			grupa = Group.loadGroupById(con, 10);
//			grupa.delete(con);
//			
//			Group[] grupa4 = Group.loadAllGroup(con);
//			
//			for ( Group grupaa : grupa4) {
//				System.out.println((grupaa.getId()+" "+grupaa.getName()));
//			}
			
//			Exercise ex0 = new Exercise("zadanie 1", "rozwiazanie 1+1 =2 ");
//			Exercise ex1 = new Exercise("zadanie 1", "rozwiazanie 1*1 =1 ");
//			Exercise ex2 = new Exercise("zadanie 1", "rozwiazanie 1-1 =0 ");
//			Exercise ex3 = new Exercise("zadanie 1", "rozwiazanie 1/1 =1 ");
//			ex1= Exercise.loadExerciseById(con, 2);
//			ex1.setTitle("zadanie 2");
//		
//			ex2= Exercise.loadExerciseById(con, 3);
//			ex2.setTitle("zadanie 3");
//			
//			ex3=Exercise.loadExerciseById(con, 4);
//			ex3.setTitle("zadanie 4");
//			ex0.saveToDB(con);ex1.saveToDB(con);ex2.saveToDB(con);ex3.saveToDB(con);

			//			for(Exercise ex : Exercise.loadAllExercises(con) ) {
//				System.out.println(ex.getId()+" "+ex.getTitle()+" "+ex.getDescription());
//			}
//			Exercise ex0 = Exercise.loadExerciseById(con, 5);
//			ex0.delete(con);
//			
//			for(Exercise ex : Exercise.loadAllExercises(con) ) {
//				System.out.println(ex.getId()+" "+ex.getTitle()+" "+ex.getDescription());
//			}
			
			
			
//	        String dateTimeNow = getFormatedDateTimeInString.now(); 
//	        Solution sol = new Solution(dateTimeNow,null,"opiss",1,1); 
//	        sol.saveToDB(con);
			
//			Solution sol0 = Solution.loadSolutionById(con, 1);
//			Solution sol1 = Solution.loadSolutionById(con, 2);
//			sol0.setDescription("testowy opis do zmiany");
//			sol1.setDescription("TEST TEST TEST");
//			sol0.saveToDB(con);
//			sol1.saveToDB(con);
//			
//			for(Solution sol : Solution.loadAllSolutions(con)) {
//				System.out.println(sol.getId()+" "+sol.getCreated()+" "+sol.getUpdated()+" "+sol.getDescription()+" "+sol.getExercise_id()+" "+sol.getUsers_id()+"\n");
//			}
			
//			sol1.delete(con);
//			sol0.delete(con);
			
			
			
//			for(Solution sol : Solution.loadAllSolutions(con)) {
//				System.out.println(sol.getId()+" "+sol.getCreated()+" "+sol.getUpdated()+" "+sol.getDescription()+" "+sol.getExercise_id()+" "+sol.getUsers_id()+"\n");
//			}
//			
//			User user123 = new User();
//			user123 = User.loadUserById(con, 1);
//			
//			Exercise[] exallbyuser = Exercise.loadAllByUserId(con,user123);
//			
//			for ( Exercise ex : exallbyuser) {
//				System.out.println(ex);
//			}
			
//			//CHECK class User method loadAllByGrupId . WORKS OK
//			Group grupa = Group.loadGroupById(con, 2);
//			User[] lista = User.loadAllByGrupId(con, grupa);
//			for (User u : lista) {
//				System.out.println(u);
//			}
//			
//			//CHECK class Exercise method loadAllByUserId . . WORKS OK
//			User user21 = User.loadUserById(con, 3);
//			Exercise[] ex = Exercise.loadAllByUserId(con, user21);
//			for (Exercise e : ex) {
//				System.out.println(e);
//			}
//			
//			//CHECK class Solution method loadAllByExerciseId . WORKS OK
//			Exercise ex1 = Exercise.loadExerciseById(con, 2);
//			Solution[] sol321 = Solution.loadAllByExerciseId(con, ex1);
//			for(Solution ex123: sol321) {
//				System.out.println(ex123);
//			}
			
			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		Connection con = ConnectDB.connect();
		User[] user999 = User.loadAll(con);
		for ( User us : user999) {
			System.out.println(us);
		}
		
	}

}
