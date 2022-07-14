

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Coffee;

/**
 * Servlet implementation class InsertCoffeeServlet_JSONArrayjQuery
 */
@WebServlet("/InsertCoffeeServlet_JSONArrayjQuery")
public class InsertCoffeeServlet_JSONArrayjQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertCoffeeServlet_JSONArrayjQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		InputStreamReader in_r = new InputStreamReader(in, "UTF-8");
		BufferedReader bf_r = new BufferedReader(in_r);
		
		String data= "";
		String data_temp = "";
		while((data_temp=bf_r.readLine())!=null)
			data += data_temp;
		
		Gson g = new Gson();
		Coffee[] cs = g.fromJson(data, Coffee[].class);
		
		/*多筆資料一起塞入資料庫*/
		boolean b = insertTable(cs);
		if(b)
			response.getWriter().append("success");
		else
			response.getWriter().append("failed");
	}
	
	boolean insertTable(Coffee[] cs) {
		if(cs.length==0)
    		return false;
    	Connection con=null;
		PreparedStatement  insert= null;
		String insertStatement = 
				"insert into classicmodels.COFFEES( COF_NAME , SUP_ID , PRICE , SALES ,TOTAL)" +
				" values ( ? , ? ,? ,? ,?)";

	    try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
	      
	        con.setAutoCommit(false);
	        for(int i=0; i < cs.length; i++) {
	        	insert = con.prepareStatement(insertStatement);
	            insert.setString(1, cs[i].getCOF_NAME()); 
		        insert.setInt(2, cs[i].getSUP_ID());           
		        insert.setDouble(3, cs[i].getPRICE());
		        insert.setInt(4, cs[i].getSALES());
		        insert.setInt(5, cs[i].getTOTAL());
		        int r=insert.executeUpdate();
		        if(r==0){
		        	con.rollback();
		        	return true;
		        }
	        }
	        con.commit();	            
	        
	    } catch (Exception e ) {
	    	System.out.println("Insert Coffee Error:"+e.getMessage());
	    	try {
				con.rollback();
			} catch (SQLException e1) {					
				e1.printStackTrace();
			}
	    	return false;
	    }finally {
	          try {
	        	con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    	
    	return true;
    }
	
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
