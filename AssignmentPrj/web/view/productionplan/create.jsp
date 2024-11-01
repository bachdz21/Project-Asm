<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Production Plan</title>
        <script>
            function validateForm() {
                var title = document.forms["productionPlanForm"]["name"].value.trim();
                var fromDate = document.forms["productionPlanForm"]["from"].value;
                var toDate = document.forms["productionPlanForm"]["to"].value;
                var workshop = document.forms["productionPlanForm"]["did"].value;

                var errorMessage = "";

                if (title === "") {
                    errorMessage += "Please enter Plan title!\n";
                }
                if (fromDate === "") {
                    errorMessage += "Please enter Start date!\n";
                }
                if (toDate === "") {
                    errorMessage += "Please enter End date!\n";
                }
                if (workshop === "-1") {
                    errorMessage += "Please select a Workshop!\n";
                }
                // Kiểm tra ngày kết thúc có trước ngày bắt đầu không
                if (fromDate && toDate && new Date(toDate) < new Date(fromDate)) {
                    errorMessage += "End date cannot be before Start date.\n";
                }

                if (errorMessage) {
                    alert(errorMessage);
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <form name="productionPlanForm" action="create" method="POST" onsubmit="return validateForm()">
            Plan title: <input type="text" name="name"/> <br/>
            From: <input type="date" name="from"/> To: <input type="date" name="to"/> <br/>
            Workshop: 
            <select name="did">
                <option value="-1">Select Workshop</option>
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}">${d.name}</option>
                </c:forEach>
            </select> <br/>
            <table border="1px">
                <tr>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Estimate</td>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input type="hidden" value="${p.id}" name="pid"/></td>
                        <td><input type="text" name="quantity${p.id}"/></td>
                        <td><input type="text" name="estimate${p.id}"/></td>
                    </tr>   
                </c:forEach>
            </table>
            <input type="submit" value="Create"/>

            
        </form>
        <a href="../dashboard.jsp">Back to Dashboard</a>
    </body>
</html>
