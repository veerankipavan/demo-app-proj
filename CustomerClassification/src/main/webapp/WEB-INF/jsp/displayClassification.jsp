<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<html>

<head>
<title>Customer Classification</title>

<style>

#customers {
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

#customers td, #customers th {
    border: 1px solid #ddd;
    padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: left;
    background-color: #4CAF50;
    color: white;
}

#parent {
    text-align:center;
    background-color:;
    height:400px;
    width:600px;
}

.center {
 	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    margin:auto;
    background-color:;
}
.left {
	 font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    margin:auto auto auto 0;
    background-color:;
}
.right {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    margin:auto 0 auto auto;
    background-color:;
}

</style>

</head>

<body>
<div id="parent">
	<c:if test="${not empty customerClassification}">
	<div id="child1" class="left">
	
	 <b>Customer Classified as </b>&nbsp;  :${customerClassification} 
	</div>
	</c:if>
	
	<br/><br/>
	<c:if test="${not empty custtransactions}">
	<div id="child1" class="left"><b >Transactions :</b></div>
	<br/>
	<div id="child2" class="left">
		
		<table id="customers">
			<tr>
				<th>Transaction Date</th>
				<th>Transaction Amount</th>
				<th>Description</th>
			</tr>
			       
      
			<c:forEach items="${custtransactions}" var="customer">
					<fmt:parseDate var= "parsedEmpDate"  value="${customer.tranDate}"  pattern="EEE MMM d HH:mm:ss z yyyy"   />
					<fmt:formatDate value="${parsedEmpDate}" var="startFormat" pattern="dd-MM-yyyy HH:mm:ss"/>
				
				<tr>
  					<td>${startFormat}</td>
					<td>${customer.tranAmt}</td>
					<td>${customer.tranDesc}</td>
				</tr>
			</c:forEach>
		</table>	
	</div>
	</c:if>
	<br/><br/>
	<div id="child3" class="left">
	 <b>Balance  : </b>  :${balance} 
	</div>
	
	<br/><br/>

      <div id="child4" class="center">
      <form:form method = "GET" action = "/inputCustomerInfo">
		<input type = "submit" value = "Home Page"/>
	  </form:form>
    </div>


</div>
</body>
</html>


