<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách kế hoạch đã xóa</title>
    <style>
        body {
            padding-top: 200px; 
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            color: #ecf0f1;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }
        h2 {
            margin-top: 700px;
            color: #ecf0f1;
            text-align: center;
        }
        table {
            padding-top: 100px;
            /*width: 100%;*/
            max-width: 800px;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #4a6a8b;
            text-align: center;
        }
        th {
            background-color: #34495e;
            color: #ecf0f1;
        }
        td {
            background-color: #f1f1f1;
            color: #2c3e50;
        }
        .restore-btn {
            background-color: #1abc9c;
            color: #ffffff;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .restore-btn:hover {
            background-color: #16a085;
        }
        .message {
            color: #2ecc71;
            margin-bottom: 15px;
            text-align: center;
        }
        .back-link {
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
            display: block;
            text-align: center;
            padding-bottom: 50px;
        }
        .back-link:hover {
            color: #2980b9;
            margin-bottom: 100px;
            background-color: #2980b9;
        }
   
    </style>
</head>
<body>
    <h2>Danh sách Kế Hoạch Đã Xóa</h2>

    <c:if test="${not empty param.message}">
        <p class="message">${param.message}</p>
    </c:if>

    <c:if test="${not empty deletedPlans}">
        <table>
            <thead>
                <tr>
                    <th>Department Name</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Restore</th>
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
                            <form action="${pageContext.request.contextPath}/productionplan/restore" method="post" style="display:inline;">
                                <input type="hidden" name="planID" value="${plan.id}" />
                                <button type="submit" class="restore-btn" onclick="return confirm('Bạn có chắc muốn khôi phục kế hoạch này?')">Restore</button>
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

    <a href="/AssignmentPrj/home" class="back-link">Quay lại Dashboard</a>
</body>
</html>
