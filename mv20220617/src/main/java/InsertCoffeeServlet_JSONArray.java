

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.*;
import com.google.gson.*;
import model.*;



/**
 * Servlet implementation class InsertCoffeeServlet_JSONArray
 */
@WebServlet("/InsertCoffeeServlet_JSONArray")
public class InsertCoffeeServlet_JSONArray extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertCoffeeServlet_JSONArray() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("coffeeJSONArray");
		Gson g = new Gson();
		Coffee[] cs = g.fromJson(data, Coffee[].class);
		//response.getWriter().append("Coffee [] length: " + cs.length);

		/*I. 多筆資料一起塞入資料庫*/
		boolean b = insertTable(cs);
		if(b)
			response.getWriter().append("success");
		else
			response.getWriter().append("failed");
		
		/*II. 這邊是一筆一筆塞入資料庫, 不是全部一起塞入 
		boolean b = false;
		for (int i=0; i < cs.length; i++) {
			try {
				b = DBDao.InsertCoffee(cs[i].getCOF_NAME(), cs[i].getSUP_ID(), cs[i].getPRICE(), cs[i].getSALES(), cs[i].getTOTAL());
			} catch (SQLException e) {
				System.out.println("DB inset JSON err: " + e.getMessage())   ;
			}
			
			response.setContentType("text/html; charset=utf-8");
			if(b)
				response.getWriter().append("新增成功"); //第一筆就回傳response 不是全部塞入後才回傳response
			else
				response.getWriter().append("新增失敗");	
		}*/
		
		
		
		
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
