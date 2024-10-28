<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>List Plan Campaign</title>
</head>
<body>
    <h2>List of Plan Campaigns</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Plan Campaign ID</th>
                <th>Plan Name</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Estimate</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="planCampaign" items="${planCampaigns}">
                <tr>
                    <td>${planCampaign.id}</td>
                    <td>${planCampaign.plan.name}</td>
                    <td>${planCampaign.product.name}</td>
                    <td>${planCampaign.quantity}</td>
                    <td>${planCampaign.estimate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="../dashboard.jsp">Back to Dashboard</a>
</body>
</html>
