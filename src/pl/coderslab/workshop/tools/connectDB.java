package pl.coderslab.workshop.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {	

	Connection con = connect(); 
	
	public static Connection connect() {
		Connection con = null;
		String url="jdbc:mysql://127.0.0.1:3306/workshop";
		String db="workshop";
		String options="?autoReconnect=true&useSSL=false";
		String user="root";
		String password="coderslab";
		try {
			con = DriverManager.getConnection(url+db+options,user,password);
		} catch (SQLException e) {
			System.out.println("It's not a bug, it's a feature");
			e.printStackTrace();
		}
		return con;
	}
}
	
