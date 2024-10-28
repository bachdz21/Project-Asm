<%-- 
    Document   : list
    Created on : Sep 25, 2024, 4:28:57 PM
    Author     : sonnt-local
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function removeEmployee(id)
            {
                var result = confirm("Are you sure?");
                if(result)
                {
                    document.getElementById("frmRemoveEmployee"+id).submit();
                }
            }
        </script>
    </head>
    <body>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Gender</td>
                <td>Dob</td>
                <td>Address</td>
                <td></td>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
             <tr>
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>
                    <c:if test="${e.gender}">
                        <img width="16px" height="16px" src="../view/img/2009.png" alt=""/>
                    </c:if>
                    <c:if test="${!e.gender}">
                        Female
                    </c:if>
                    
                </td>
                <td>${e.dob}</td>
                <td>${e.address}</td>
                <td><a href="update?id=${e.id}">Edit</a>
                    <input type="button" value="Remove" onclick="removeEmployee(${e.id})"/>
                    <form id="frmRemoveEmployee${e.id}" action="delete" method="POST">
                        <input type="hidden" name="id" value="${e.id}"/>
                    </form>
                </td>
            </tr>   
                
            </c:forEach>
            
        </table>
    </body>
</html>
