package pl.coderslab.workshop.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.workshop.jbcrypt.BCrypt;

public class User {
	private	int id;
	private String email;
	private	String username;
	private	String password;
	private String token;
	private String tokenExpire;
	private int loginTries;
	private String lastLoginDate;
	
	public User() {}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toStringasd() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", token="
				+ token + ", tokenExpire=" + tokenExpire + ", loginTries=" + loginTries + ", lastLoginDate="
				+ lastLoginDate + "]";
	}
	
	@Override
	public String toString() {
		return "[" + id + ", " + email + ", " + username + ", " + password + ", " + token + ", " + tokenExpire + ", "
				+ loginTries + ", " + lastLoginDate + "] ";
	}

	/** Zwraca tylko te pola ktore sa wymagane aby utworzyc obiekt User<br>  
	 * @param option <br> <b>req</b> tylko pola wymaganie <br>
	 * <b>pola</b> tylko nazwy wszystkich pol<br>
	 * <b>wartosci</b> tylko wartosci z wszystkich pol<br>
	 * <b>zande z powyzszych </b> zwraca pole z wartoscia tego pola
	 * @return - <tt>String</tt> wg wybrango parametru
	 */
	public String toString(String option) {
		if(option.equalsIgnoreCase("req")) {
			return "Wymagane pola dla User [email, username,password]";
		} else if (option.equalsIgnoreCase("pola")){
			return "[id, email, username, password, token, tokenExpire, loginTries, lastLoginDate]";
		} else if (option.equalsIgnoreCase("wartosci")) {
			return "[" + id + ", " + email + ", " + username + ", " + password + ", " + token + ", " + tokenExpire + ", "
					+ loginTries + ", " + lastLoginDate + "] ";
		} else {
			return this.toString();
		}
	}

	public	User(String email,String username, String password)	{
		this.email = email;
		this.username =	username;
		this.setPassword(password);
	}	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the username
	 */	
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password =	BCrypt.hashpw(password,	BCrypt.gensalt());
	}
		
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**zapisuje/edytuje do bazy danych obiekt SAVE(insert into) or UPDATE(update) 
	 * @param conn - polaczenie do bazy danych
	 * @throws SQLException
	 */
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Users(username, email, password) VALUES (?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql = "UPDATE Users SET username=?, email=?, password=? where id = ?";
		    PreparedStatement preparedStatement;
		    preparedStatement = conn.prepareStatement(sql);
		    preparedStatement.setString(1, this.username);
		    preparedStatement.setString(2, this.email);
		    preparedStatement.setString(3, this.password);
		    preparedStatement.setInt(4, this.id);
		    preparedStatement.executeUpdate();
		}
	}
	/** wczytuje z bazy danych usera o id i zwraca obiekt user
	 * @param conn - polaczenie do bazy danych
	 * @param id - id usera do wczytania
	 * @return obiekt user pobrany z bazy danych
	 * @throws SQLException
	 */
	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Users where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			return loadedUser;
		}
		return null;
	}

	/** zwraca tablice z obiektami typu User ( nasza klasa ) 
	 * @param conn - typ Connection jako parametr 
	 * @return - zwraca ArrayList<User>
	 * @throws SQLException 
	 */
	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM Users";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}
	/** usuwanie uzytkowanika typ klasy User 
	 * @param con parametr polaczenia
	 * @throws SQLException 
	 */
	public void delete(Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM Users WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	/**pobranie wszystkich członków danej grupy 
	 * (dopisz metodę  loadAllByGrupId  do klasy  User )
	 * SELECT * FROM `Users` WHERE `person_group_id` = ?
	 * @param conn
	 * @param group
	 * @return
	 * @throws SQLException
	 */
	static public User[] loadAllByGrupId(Connection conn, Group group) throws SQLException {
		ArrayList<User> loadedAllByGrupId = new ArrayList<User>();
		String sql = "SELECT * FROM `Users` WHERE `person_group_id` = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, group.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUserById = new User();
			loadedUserById.id = resultSet.getInt("id");
			loadedUserById.username = resultSet.getString("username");
			loadedUserById.password = resultSet.getString("password");
			loadedUserById.email = resultSet.getString("email");
			loadedAllByGrupId.add(loadedUserById);
		}
		User[] loadAllByGrupId = new User[loadedAllByGrupId.size()];
		loadAllByGrupId = loadedAllByGrupId.toArray(loadAllByGrupId);
		return loadAllByGrupId;
	}
	
}
