<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Guess JSP</title>
</head>
<body>
	<jsp:useBean id="Fortune" class="model.GuessGameLogic" scope="session"> <!-- jsp:useBean 宣告類別變數的語法(替代java)  -->
		<% Fortune.initialize(1,10); %>
	</jsp:useBean>
	<%
		String guess = request.getParameter("number");
		int guessNum = Integer.parseInt(guess);
		if (Fortune.isCorrectGuess(guessNum)) {
			session.invalidate();
	%>
	<jsp:forward page="bingo.jsp" />
	<%
		} else {
			int remainder = Fortune.getRemainder();
		  		if (remainder > 0) {
	%>
	The number <%= guess %> is not correct.</br>
	You still have <%= remainder %> chances.</br>
	<%= Fortune.getHint() %> <br/>
	<a href="guess.html">Try again</a>
	<%
				} else {
		    		session.invalidate();
	%>
	<jsp:forward page="noChances.jsp" />
	<%
				}
		}
    %>
	
	
	
</body>
</html>