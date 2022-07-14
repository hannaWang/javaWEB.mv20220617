package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBDao {
	//Insert
	public static boolean InsertCoffee(String coffee, int supplier, double price, int sales, int total) throws SQLException {
		Connection con=null;
		PreparedStatement  insert= null; //SQL String w/ ? use PreparedStatement 
		String insertStatement = "insert into classicmodels.COFFEES(COF_NAME , SUP_ID , PRICE , SALES ,TOTAL)" +
		        "values ( ? , ? ,? ,? ,?)";
		
	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");	        
	        con.setAutoCommit(false); //交易控制, false:要執行connection.commit()才送出 否則roll back	        
	        insert = con.prepareStatement(insertStatement);
	        	        
	        insert.setString(1, coffee);  //setString(parameterIndex, String)
	        insert.setInt(2, supplier);           
	        insert.setDouble(3, price);
	        insert.setInt(4, sales);
	        insert.setInt(5, total);
	        int row = insert.executeUpdate(); //執行 SQL Update, 接收SQL_DB回傳的筆數值
	        con.commit();
	       
	        //傳出boolean值, 讓前端顯示新增成功與否
	        if(row>0)
	        	return true;
	        else
	        	return false;
	        
	    } catch (Exception e ) {
	    	System.out.println(e.getMessage());
		    if (con != null) {
		    	try {
		    		System.err.print("Transaction is being rolled back");
		            con.rollback();
		        } catch(SQLException ex) {
		            System.out.println(ex.getMessage());
		        }}
		    return false;
		} finally {
			if (insert != null) {
				insert.close();
		    }
		    con.setAutoCommit(true);
		}
	}
	
	//Update
	public static boolean updateCoffeeSales(String coffee,String sale,String total) throws SQLException {
		Connection con=null;
	    PreparedStatement updateSales = null;
		PreparedStatement updateTotal = null;

	    String updateString =
	        "update classicmodels.COFFEES " +
	        "set SALES = ? where COF_NAME = ?";

	    String updateStatement =
	        "update classicmodels.COFFEES " +
	        "set TOTAL = TOTAL + ? " +
	        "where COF_NAME = ?";

	    try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
	      
	        con.setAutoCommit(false);
	        updateSales = con.prepareStatement(updateString);
	        updateTotal = con.prepareStatement(updateStatement);

            updateSales.setInt(1, Integer.parseInt(sale));
            updateSales.setString(2, coffee);
            int rS = updateSales.executeUpdate();
            updateTotal.setInt(1, Integer.parseInt(total));
            updateTotal.setString(2, coffee);
            int rT = updateTotal.executeUpdate();
            
            if (rS>0 && rT>0){
            	con.commit();
            	return  true;
            }
            else{
            	con.rollback();
            	return  false;
            }
	    } catch (Exception e ) {
	        System.out.println(e.getMessage());
	        if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException excep) {
	                System.out.println(e.getMessage());
	            }
	        }
	        return  false;
	    } finally {
	    	if (updateSales != null) {
	            updateSales.close();
	        }
	        if (updateTotal != null) {
	            updateTotal.close();
	        }
	        con.setAutoCommit(true);
	    }
	}

	//Delete
	public static boolean DeleteCoffee(String coffee)  throws SQLException {
	    Connection con=null;
	    PreparedStatement  delete= null;
	   
	    String insertStatement =
	        "delete from COFFEES " +
	        "where COF_NAME=? ";

	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
		    con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
		  
	        con.setAutoCommit(false);    
	        delete = con.prepareStatement(insertStatement);

	        delete.setString(1, coffee);          
	        int rd = delete.executeUpdate();
	        if(rd > 0){
	        	con.commit();
	        	return true;
	        }else {
	        	con.rollback();
	        	return false;
	        }	
	    } catch (Exception e ) {
	        System.out.println(e.getMessage());
	        if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	        return false;
	    } finally {
	        if (delete != null) {
	            delete.close();
	        }
	        con.setAutoCommit(true);
	    }
	}  

	
	
}
