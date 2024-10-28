<%-- 
    Document   : update
    Created on : Sep 21, 2024, 2:04:01 PM
    Author     : sonnt-local
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../master/greeting.jsp"></jsp:include>
            <form action="update" method="POST">
                Id: ${requestScope.e.id} <br/>
            <input type="hidden" name="id" value="${requestScope.e.id}"/>
            Name: <input type="text" name="name" value="${requestScope.e.name}"/> <br/>
            Gender: <input type="radio" name="gender" value="male" 
                           ${requestScope.e.gender?"checked=\"checked\"":""}
                           /> Male
            <input type="radio" name="gender" value="female"
                   ${!requestScope.e.gender?"checked=\"checked\"":""}
                   /> Female <br/>
            Dob :<input type="date" name="dob" value="${requestScope.e.dob}" /> <br/>
            Address: <input type="text" name="address" value="${requestScope.e.address}"/> <br/>
            Department: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option 
                        ${requestScope.e.dept.id eq d.id?"selected=\"selected\"":""}
                        value="${d.id}">${d.name}</option>
                </c:forEach>
            </select> <br/>
            Created by: ${requestScope.e.createdby.displayname} <br/>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
