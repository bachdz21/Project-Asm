<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>  
        <title>Dashboard</title>
    </head>
    <body>
        <h2>Dashboard</h2>
        <ul>
            <li><a href="/AssignmentPrj/productionplan/list">List Plan</a></li>
            <li><a href="/AssignmentPrj/productionplan/create">Create Plan</a></li>
            <li><a href="/AssignmentPrj/product/list">List Product</a></li>
            <li><a href="/AssignmentPrj/employee/list">List Employee</a></li>        
            <li><a href="/AssignmentPrj/productionplan/restore">History Delete Plan</a></li>

            <li><a href="logout">Logout</a></li>
        </ul>

        <c:if test="${not empty sessionScope.message}">
            <p class="message">${sessionScope.message}</p>
            <c:remove var="message" scope="session" />
        </c:if>

        
    </body>
</html>