<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title></title>
<style type="text/css">
.center {
 	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    margin:auto;
    background-color:;
}
</style>
</head>

<body>
<div>
	<h2 align="center">Enter Customer Information</h2>
	
	<form:form method = "POST" action = "/displayClassification">
        
         <table  class="center">
            <tr>
               <td><form:label path = "custid">Customer Id</form:label></td>
               <td><form:input path="custid"/></td>
            </tr>
            <tr>
               <td><form:label path = "transMonth">Month</form:label></td>
               <td><form:select path="transMonth" items="${months}"/></td>
            </tr>
            <tr>
               <td><form:label path = "year">Year</form:label></td>
               <td><form:select path="year" items="${year}"/></td>
            </tr>
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
       
      </form:form>
      </div>
</body>

</html>