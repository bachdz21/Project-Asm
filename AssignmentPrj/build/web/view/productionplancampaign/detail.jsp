<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Chi tiết Kế Hoạch Sản Xuất</title>
    </head>
    <body>
        <h2>Chi tiết Kế Hoạch Sản Xuất</h2>


        <c:if test="${not empty planCampaigns}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Plan Name</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Estimate</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="currentPlanName" value="" />
                    <c:set var="PlanId" value="" />
                    <c:forEach var="planCampaign" items="${planCampaigns}" varStatus="status">
                        <tr>
                            <c:if test="${currentPlanName != planCampaign.plan.name}">
                                <td rowspan="${fn:length(planCampaigns.stream().filter(pc -> pc.plan.name.equals(planCampaign.plan.name)).toList())}">
                                    ${planCampaign.plan.name}
                                </td>
                                <c:set var="currentPlanName" value="${planCampaign.plan.name}" />
                            </c:if>
                            <td>${planCampaign.product.name}</td>
                            <td>${planCampaign.quantity}</td>
                            <td>${planCampaign.estimate}</td>
                            <c:set var="PlanId" value="${planCampaign.plan.id}" /> <!-- Gán giá trị id -->
                        </tr>
                    </c:forEach>

            </tbody>
        </table>
    </c:if>

    <c:if test="${empty planCampaigns}">
        <p>No Plan Campaigns available for this Plan ID.</p>
    </c:if>

    <<br>
    <a href="../dashboard.jsp">Quay lại Dashboard</a> |
    <a href="${pageContext.request.contextPath}/schedualcampaign/create?planID=${PlanId}">Edit</a>

</body>
</html>
