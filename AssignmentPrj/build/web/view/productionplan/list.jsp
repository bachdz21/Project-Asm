<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách kế hoạch sản xuất</title>
    </head>
    <body>
        <h2>Danh sách Kế Hoạch Sản Xuất</h2>
        <c:if test="${not empty plansByDepartment}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Department Name</th>
                        <th>Plan Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Detail</th> <!-- Cột mới để chứa liên kết đến chi tiết -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${plansByDepartment}">
                        <c:set var="departmentName" value="${entry.key}" />
                        <c:set var="plans" value="${entry.value}" />
                        <tr>
                            <td rowspan="${fn:length(plans)}">${departmentName}</td>
                            <td>${plans[0].name}</td>
                            <td>${plans[0].start}</td>
                            <td>${plans[0].end}</td>
                            <td>
                                <!-- Thêm liên kết chi tiết chuyển đến `detail.jsp` -->
                                <a href="${pageContext.request.contextPath}/productionplancampaign/detail?planId=${plans[0].id}">Detail</a>
                            </td>
                        </tr>
                        <c:forEach var="plan" items="${plans}" varStatus="status">
                            <c:if test="${!status.first}">
                                <tr>
                                    <td>${plan.name}</td>
                                    <td>${plan.start}</td>
                                    <td>${plan.end}</td>
                                    <td>
                                        <!-- Thêm liên kết chi tiết chuyển đến `detail.jsp` -->
                                        <a href="${pageContext.request.contextPath}/productionplancampaign/detail?planId=${plan.id}">Detail</a>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty plansByDepartment}">
            <p>No plans available.</p>
        </c:if>

        <br>
        <a href="../dashboard.jsp">Quay lại Dashboard</a>
    </body>
</html>
