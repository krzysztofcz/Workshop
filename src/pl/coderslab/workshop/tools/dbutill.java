package pl.coderslab.workshop.tools;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.mysql.jdbc.Connection;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

/**
 * Servlet implementation class dbutill
 */
@WebServlet("/dbutill")
public class dbutill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dbutill() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		public class DbUtil {
			private static DataSource ds;

			public static Connection getConn() throws SQLException {
				return getInstance().getConnection();
			}

			private static DataSource getInstance() {
				if (ds == null) {
					try {
						Context ctx = new InitialContext();
						ds = (DataSource) ctx.lookup("java:comp/env/jdbc/workshop");
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
				return ds;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
