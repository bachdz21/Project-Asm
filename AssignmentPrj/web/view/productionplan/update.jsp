<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Production Plan</title>
        <script>
            function validateForm() {
                // Lấy các trường cần kiểm tra
                const planTitle = document.forms["updateForm"]["name"].value;
                const fromDate = document.forms["updateForm"]["from"].value;
                const toDate = document.forms["updateForm"]["to"].value;
                const workshop = document.forms["updateForm"]["did"].value;

                // Kiểm tra các trường để trống và hiển thị thông báo nếu có
                const from = new Date(fromDate);
                const to = new Date(toDate);
                if (from >= to) {
                    alert("The From date must be earlier than the To date");
                    return false;
                }
                if (planTitle === "") {
                    alert("Plan Title cannot be empty");
                    return false;
                }
                if (fromDate === "") {
                    alert("From date cannot be empty");
                    return false;
                }
                if (toDate === "") {
                    alert("To date cannot be empty");
                    return false;
                }
                if (workshop === "") {
                    alert("Please select a Workshop");
                    return false;
                }
                return true; // Nếu tất cả các trường đều hợp lệ, cho phép gửi biểu mẫu
            }
        </script>
    </head>
    <body>
        <h2>Update Production Plan</h2>
        <form name="updateForm" action="update" method="POST" onsubmit="return validateForm()">
            <input type="hidden" name="planID" value="${plan.id}"/>
            Plan Title: <input type="text" name="name" value="${plan.name}"/> <br/>
            From: <input type="date" name="from" value="${plan.start}"/> 
            To: <input type="date" name="to" value="${plan.end}"/> <br/>
            
            <!-- Dropdown Workshop -->
            Workshop:
            <select name="did">
                <c:forEach items="${depts}" var="d">
                    <option value="${d.id}" ${plan.dept.id == d.id ? 'selected="selected"' : ''}>${d.name}</option>
                </c:forEach>
            </select> <br/>

            <!-- Hiển thị tất cả các sản phẩm -->
            <table border="1">
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Estimate</th>
                </tr>
                
                <c:forEach items="${products}" var="product">
                    <c:set var="existingCampaign" value="${null}" />

                    <!-- Kiểm tra xem sản phẩm này đã có trong plan chưa -->
                    <c:forEach items="${plan.campaigns}" var="campaign">
                        <c:if test="${campaign.product.id == product.id}">
                            <c:set var="existingCampaign" value="${campaign}" />
                        </c:if>
                    </c:forEach>
                    
                    <tr>
                        <td>${product.name}
                            <input type="hidden" name="pid" value="${product.id}"/>
                        </td>
                        <td>
                            <input type="text" name="quantity${product.id}" 
                                   value="${existingCampaign != null ? existingCampaign.quantity : ''}"/>
                        </td>
                        <td>
                            <input type="text" name="estimate${product.id}" 
                                   value="${existingCampaign != null ? existingCampaign.estimate : ''}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <input type="submit" value="Save"/>
        </form>
        <br>
        <a href="../dashboard.jsp">Back to Dashboard</a>
    </body>
</html>
