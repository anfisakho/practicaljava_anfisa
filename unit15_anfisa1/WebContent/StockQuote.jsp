<%@ page import="StockServer.*"%>
<%@ page language="java" import="java.util.*"%>

<html>
<head>
<title>Find a Quote</title>
</head>

<body>

	<%--
	  out.println(StockServer.getYahooQuote("AAPL"));
	--%>

  ${header.host} Speaking ...<br>

	Enter quote name in the following form:

	<br> ( For example, "EPAM", "LXFT", "AAPL" or "IBM" )
	<form action=http://localhost:8080/unit15_anfisa1/StockQuote.jsp
		method=Post>
		<input type=Text name=symbol> <input type=Submit
			value="Search for quote">
	</form>

	<%@ page errorPage="StockServerError.jsp"%>

	<%
	  String cookieName = "haveJustVisited";
	  Cookie cookies[] = request.getCookies();
	  Cookie myCookie = null;
	  if (cookies != null) {
	    for (int i = 0; i < cookies.length; i++) {
	      if (cookies[i].getName().equals(cookieName)) {
	        myCookie = cookies[i];
	        break;
	      }
	    }
	  }
	%>


	<%
	  if (myCookie == null) {

	    String haveJustVisited = "yes";
	    Cookie cookie = new Cookie("haveJustVisited", haveJustVisited);
	    cookie.setMaxAge(100);
	    response.addCookie(cookie);
	    
	    out.println("<p>P.S. Thanks for opening me for the first time! :)");

	  } else {

	    out.println("<p>I've seen you here!!! :)");

	  }
	%>

	<%
	  String symbol = request.getParameter("symbol");

	  if (symbol == null) {
	    symbol = "NULL";
	  } //protection against first run with empty symbol string 
	  if (symbol.length() == 0) {
	    symbol = "NULL";
	  } //protection against test with empty symbol text field submitted 

	  String symbolPrice = StockServer.getYahooQuote(symbol);

	  if (Float.parseFloat(symbolPrice) > 0) {
	    out.println("<h2>The " + symbol + " share costs " + symbolPrice
	        + "</h2>");
	    // out.println("<p>How many shares would you like to buy?");
	  } else if (myCookie != null) {
	    out.println("<h2>I was unable to perform a request. Please enter the name again or try later on. </h2>");
	  }
	%>



</body>
</html>