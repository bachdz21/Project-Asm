<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách kế hoạch đã xóa</title>
</head>
<body>
    <h2>Danh sách Kế Hoạch Đã Xóa</h2>

    <c:if test="${not empty param.message}">
        <p style="color: green;">${param.message}</p>
    </c:if>

    <c:if test="${not empty deletedPlans}">
        <table border="1">
            <thead>
                <tr>
                    <th>Department Name</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Restore</th> <!-- Thêm cột Restore -->
                </tr>
            </thead>
            <tbody>
                <c:forEach var="plan" items="${deletedPlans}">
                    <tr>
                        <td>${plan.dept.name}</td>
                        <td>${plan.name}</td>
                        <td>${plan.start}</td>
                        <td>${plan.end}</td>
                        <td>
                            <!-- Nút Restore gửi yêu cầu POST tới ProductionPlanRestoreController -->
                            <form action="${pageContext.request.contextPath}/productionplan/restore" method="post" style="display:inline;">
                                <input type="hidden" name="planID" value="${plan.id}" />
                                <button type="submit" onclick="return confirm('Bạn có chắc muốn khôi phục kế hoạch này?')">Restore</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty deletedPlans}">
        <p>Không có kế hoạch nào đã xóa.</p>
    </c:if>

    <br>
    <a href="../dashboard.jsp">Quay lại Dashboard</a>
</body>
</html>