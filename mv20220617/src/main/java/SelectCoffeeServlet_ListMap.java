

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SelectCoffeeServlet_ListMap
 */
@WebServlet("/SelectCoffeeServlet_ListMap")
public class SelectCoffeeServlet_ListMap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectCoffeeServlet_ListMap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map> data = getCoffee();
		request.setAttribute("coffees", data);
		request.getRequestDispatcher("viewCoffee.jsp").forward(request, response);
	}

	List<Map> getCoffee() {
		List<Map> data = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		String sql = "select*from classicmodels.COFFEES";
		
		try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
	    	st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Map m = new HashMap();
				m.put("coffee", rs.getString("COF_NAME"));
				m.put("supplier", rs.getInt("SUP_ID"));
				m.put("price", rs.getDouble("price"));
				m.put("sales", rs.getInt("sales"));
				m.put("total", rs.getInt("total"));
				data.add(m);
				//System.out.println(m);
			}

			
	    } catch (Exception e ) {
	    	System.out.println(e.getMessage());
		}
		
		return data;
	}
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
