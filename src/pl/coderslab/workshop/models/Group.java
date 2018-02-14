/**
 * 
 */
package pl.coderslab.workshop.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.workshop.mysql.ConnectDB;


/**
 * @author kcz
 * DAO - loadAll,loadById,delete,saveToDB
 */
public class Group {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + id + ", " + name + "]";
	}
	
	public String toString(String option) {
		if(option.equalsIgnoreCase("req+pola")) {
			return "Name";
		} else if (option.equalsIgnoreCase("req+wartosci")){
			return name;
		} else if (option.equalsIgnoreCase("pola")){
			return "[id,name]";
		} else if (option.equalsIgnoreCase("wartosci")){
			return id+","+name;
		} else if (option.equalsIgnoreCase("pola+wartosci")) {
			return "Group [id=" + id + ", name=" + name +"]";
		} else {
			return this.toString();
		}
	}
	
	
	private int id;
	private String name;
	
	public Group() {};
	
	/**
	 * @param id
	 * @param name
	 */
	public Group(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** zapisauje do bazy danych lub robi update wpisu juz istenijacego
	 * @throws SQLException
	 */
	public void saveToDB() throws SQLException {
		Connection conn = ConnectDB.connect();
		if (this.id == 0) {
			String sql = "INSERT INTO user_group(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.name);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql = "UPDATE user_group SET name=? where id = ?";
		    PreparedStatement preparedStatement;
		    preparedStatement = conn.prepareStatement(sql);
		    preparedStatement.setString(1, this.name);
		    preparedStatement.setInt(2, this.id);
		    preparedStatement.executeUpdate();
		}
	}
	/** loaduje grupe by id to obiektu Group
	 * @param id - id grupy
	 * @return
	 * @throws SQLException
	 */
	static public Group loadById(Integer id) throws SQLException {
		String sql = "SELECT * FROM user_group where id=?";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			return loadedGroup;
		}
		return null;
	}
	/** zwraca tablice typu Group[] z wszystkimi grupami
	 * @return
	 * @throws SQLException
	 */
	static public Group[] loadAll() throws SQLException {
		ArrayList<Group> group = new ArrayList<Group>();
		String sql = "SELECT * FROM user_group";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			group.add(loadedGroup);
		}
		Group[] groupArray = new Group[group.size()];
		groupArray = group.toArray(groupArray);
		return groupArray;
	}
	/** remove/delete group from DB
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM user_group WHERE id= ?";
	        PreparedStatement preparedStatement;
	        Connection conn = ConnectDB.connect();
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	public Group clear() {
		this.id=0;
		this.setName(null);
		return this;
	}
}
