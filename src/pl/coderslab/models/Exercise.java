package pl.coderslab.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {

	private int id;
	private String title;
	private String description;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Exercise [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
	
	
	/**
	 *  empty constructor
	 */
	public Exercise() {
	}

	/**
	 * @param title
	 * @param text
	 */
	public Exercise(String title, String text) {
		this.title = title;
		this.description = text;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the text
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param text the text to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	/** INSERT INTO or UPDATE 
	 * @param conn
	 * @throws SQLException
	 */
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			} 
		} else {
			String sql = "UPDATE exercise SET title=?, description=? WHERE id = ?";
		    PreparedStatement preparedStatement;
		    preparedStatement = conn.prepareStatement(sql);
		    preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
		    preparedStatement.executeUpdate();
		}
	}
	
	/**loadExerciseById , SELECT * FROM exercise where ID=
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM exercise where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			return loadedExercise;
		}
		return null;
	}

	/** SELECT * FROM Exercise";
	 * @param conn
	 * @return tablica typu Excercise[] ze wszystkimi zadaniami
	 * @throws SQLException
	 */
	static public Exercise[] loadAllExercises(Connection conn) throws SQLException {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM exercise";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			exercises.add(loadedExercise);
		}
		Exercise[] exercisesArray = new Exercise[exercises.size()];
		exercisesArray = exercises.toArray(exercisesArray);
		return exercisesArray;
	}
	/** DELETE FROM Exercise WHERE id= ? 
	 * @param con parametr polaczenia
	 * @throws SQLException 
	 */
	public void delete(Connection conn) throws SQLException {
	    if (this.id != 0) {
	        String sql = "DELETE FROM exercise WHERE id= ?";
	        PreparedStatement preparedStatement;
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, this.id);
	        preparedStatement.executeUpdate();
	        this.id=0;
	    }
	}
	
	public static Exercise[] loadAllByUserId(Connection conn, User user) throws SQLException {
		
//		SELECT exercise.id,exercise.title,exercise.description FROM exercise JOIN solution ON exercise.id=solution.exercise_id JOIN Users ON solution.users_id = Users.id WHERE Users.id=3
		
		if(user.getId()!=0) {
		ArrayList<Exercise> exercisesByUserId = new ArrayList<Exercise>();
		String sql = "SELECT exercise.id,exercise.title,exercise.description "
				+ "FROM exercise JOIN solution ON exercise.id=solution.exercise_id "
				+ "JOIN Users ON solution.users_id = Users.id "
				+ "WHERE Users.id= ? ";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, user.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExerciseByUserId = new Exercise();
			loadedExerciseByUserId.id = resultSet.getInt("id");
			loadedExerciseByUserId.title = resultSet.getString("title");
			loadedExerciseByUserId.description = resultSet.getString("description");
			exercisesByUserId.add(loadedExerciseByUserId);
		}
		Exercise[] exercisesByUserIdArray = new Exercise[exercisesByUserId.size()];
		exercisesByUserIdArray = exercisesByUserId.toArray(exercisesByUserIdArray);
		return exercisesByUserIdArray;
		} else {
			return null;
		}
	}
}
