package pl.coderslab.workshop.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.workshop.mysql.ConnectDB;
import pl.coderslab.workshop.tools.getFormatedDateTimeInString;

public class Solution {
	@Override
	public String toString() {
		return "[" + id + ", " + created + ", " + updated + ", " + description + ", " + exercise_id + ", " + users_id + "]";
	}
	
	public String toString(String option) {
		if(option.equalsIgnoreCase("req+pola")) {
//		WAZNE musza byc z duzej listery Exercise and User i nie moze byc spacji 
//		miedzy , "przecinkiem" a nawa pola
			return "Exercise_id,Users_id"; 
		} else if (option.equalsIgnoreCase("req+wartosci")){
			return (exercise_id + "," + users_id);
		} else if (option.equalsIgnoreCase("pola")){
			return "[id, created, updated, description, exercise_id, users_id]";
		} else if (option.equalsIgnoreCase("wartosci")){
			return (id + "," + created + "," + updated + "," + description + "," + exercise_id + "," + users_id);
		} else if (option.equalsIgnoreCase("pola+wartosci")) {
			return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", description=" + description
					+ ", exercise_id=" + exercise_id + ", users_id=" + users_id + "]";
		} else {
			return this.toString();
		}
	}
	
	/**
	 * empty constructor
	 */
	public Solution() {
	}
	/**
	 * @param created
	 * @param updated
	 * @param description
	 * @param exercise_id
	 * @param users_id
	 */
	public Solution(String created, String description, int exercise_id, int user_id) {
		this.created = created;
		this.description = description;
		this.exercise_id = exercise_id;
		this.users_id = user_id;
	}

	private int id;
	private String created;
	private String updated;
	private String description;
	private int exercise_id;
	private int users_id;

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}
	
	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the exercise_id
	 */
	public int getExercise_id() {
		return exercise_id;
	}
	/**
	 * @param exercise_id the exercise_id to set
	 */
	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}
	/**
	 * @param exercise_id the exercise_id to set
	 */
	public void setExercise_id(Integer exercise_id) {
		this.exercise_id = exercise_id;
	}
	/**
	 * @return the users_id
	 */
	public int getUsers_id() {
		return users_id;
	}
	/**
	 * @param users_id the users_id to set
	 */
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	/**
	 * @param users_id the users_id to set
	 */
	public void setUsers_id(Integer users_id) {
		this.users_id = users_id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/** INSERT INTO and UPDATE do DB
	 * @throws SQLException
	 */
	public void saveToDB() throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solution(created, description, exercise_id, users_id) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			Connection conn = ConnectDB.connect();
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, getFormatedDateTimeInString.now());
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.exercise_id);
			preparedStatement.setInt(4, this.users_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql = "UPDATE solution SET updated=?, description=?, exercise_id=?, users_id=? WHERE id = ?";
		    PreparedStatement preparedStatement;
		    Connection conn = ConnectDB.connect();
		    preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, getFormatedDateTimeInString.now());
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.exercise_id);
			preparedStatement.setInt(4, this.users_id);
			preparedStatement.setInt(5, this.id);
		    preparedStatement.executeUpdate();
		}
	}
	
	/** SELECT * FROM solution where id=? 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	static public Solution loadById(int id) throws SQLException {
		String sql = "SELECT * FROM solution where id=?";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated= resultSet.getString("updated");
			loadedSolution.description= resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.users_id = resultSet.getInt("users_id");
			return loadedSolution;
		}
		return null;
	}
	
	/** SELECT * FROM solution
	 * @return
	 * @throws SQLException
	 */
	static public Solution[] loadAll() throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getString("created");
			loadedSolution.updated= resultSet.getString("updated");
			loadedSolution.description= resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("exercise_id");
			loadedSolution.users_id = resultSet.getInt("users_id");
			solutions.add(loadedSolution);
		}
		Solution[] solutionsArray = new Solution[solutions.size()];
		solutionsArray = solutions.toArray(solutionsArray);
		return solutionsArray;
	}
	
	/**DELETE FROM solution WHERE id= ?
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM solution WHERE id= ?";
	        PreparedStatement preparedStatement;
	        Connection conn = ConnectDB.connect();
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	/** pobranie wszystkich rozwiązań danego zadania posortowanych od najnowszego do najstarszego
	 * (dopisz metodę loadAllByExerciseId do klasy Solution )
	 * SELECT * FROM solution WHERE exercise_id = 2 ORDER BY created DESC
	 * @param exercise
	 * @return Tablice z typami Solution[]
	 * @throws SQLException
	 */
	public static Solution[] loadAllByExerciseId(Exercise exercise) throws SQLException {
		ArrayList<Solution> solutionsloadAllByExerciseId = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE exercise_id = ? ORDER BY created DESC";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, exercise.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolutionByExerciseId = new Solution();
			loadedSolutionByExerciseId.id = resultSet.getInt("id");
			loadedSolutionByExerciseId.created = resultSet.getString("created");
			loadedSolutionByExerciseId.updated= resultSet.getString("updated");
			loadedSolutionByExerciseId.description= resultSet.getString("description");
			loadedSolutionByExerciseId.exercise_id = resultSet.getInt("exercise_id");
			loadedSolutionByExerciseId.users_id = resultSet.getInt("users_id");
			solutionsloadAllByExerciseId.add(loadedSolutionByExerciseId);
		}
		Solution[] solutionsAllByExerciseIdArray = new Solution[solutionsloadAllByExerciseId.size()];
		solutionsAllByExerciseIdArray = solutionsloadAllByExerciseId.toArray(solutionsAllByExerciseIdArray);
		return solutionsAllByExerciseIdArray;
	}
	
	/** view - zapyta o id użytkownika którego rozwiązania chcemy zobaczyć.
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static Solution[] loadAllByUsersId(User user) throws SQLException {
		ArrayList<Solution> solutionsLoadedAllByUsersId = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE users_id = ? ORDER BY created DESC";
		PreparedStatement preparedStatement;
		Connection conn = ConnectDB.connect();
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, user.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolutionByUsersId = new Solution();
			loadedSolutionByUsersId.id = resultSet.getInt("id");
			loadedSolutionByUsersId.created = resultSet.getString("created");
			loadedSolutionByUsersId.updated= resultSet.getString("updated");
			loadedSolutionByUsersId.description= resultSet.getString("description");
			loadedSolutionByUsersId.exercise_id = resultSet.getInt("exercise_id");
			loadedSolutionByUsersId.users_id = resultSet.getInt("users_id");
			solutionsLoadedAllByUsersId.add(loadedSolutionByUsersId);
		}
		Solution[] solutionsAllByExerciseIdArray = new Solution[solutionsLoadedAllByUsersId.size()];
		solutionsAllByExerciseIdArray = solutionsLoadedAllByUsersId.toArray(solutionsAllByExerciseIdArray);
		return solutionsAllByExerciseIdArray;
	}
	
	public Solution clear() {
		this.id=0;
		this.created=null;
		this.updated=null;
		this.setDescription(null);
		this.setExercise_id(0);
		this.setUsers_id(0);
		return this;
	}
}
