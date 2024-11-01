<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #1e2a38;
                color: #ecf0f1;
                margin: 0;
                padding: 0;
                display: flex;
            }
            .sidebar {
                background-color: #243447;
                width: 200px;
                padding: 20px;
                height: 100vh;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
            }
            .sidebar h4 {
                color: #ecf0f1;
                margin-bottom: 15px;
                font-weight: bold;
            }
            .sidebar p {
                color: #7f8c8d;
                font-size: 14px;
                margin-bottom: 15px;
            }
            .sidebar a {
                color: #3498db;
                text-decoration: none;
                display: block;
                margin: 10px 0;
                font-weight: bold;
            }
            .sidebar a:hover {
                color: #2980b9;
            }
            .content {
                flex-grow: 1;
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            h2 {
                color: #ffffff;
                margin-bottom: 20px;
            }
            .board {
                display: flex;
                gap: 20px;
                max-width: 1000px;
                width: 100%;
            }
            .column {
                background: #2c3e50;
                padding: 20px;
                width: 300px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .column h3 {
                font-size: 18px;
                color: #ecf0f1;
                margin-bottom: 15px;
                text-align: center;
            }
            .plan-list {
                list-style: none;
                padding: 0;
                width: 100%;
            }
            .plan-list li {
                background: #1abc9c;
                color: #ecf0f1;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 10px;
                text-align: center;
                font-weight: bold;
            }
            .plan-list li:hover {
                background: #16a085;
            }
            .add-card {
                color: #8e44ad;
                font-size: 14px;
                text-decoration: none;
                margin-top: 10px;
                display: inline-block;
                font-weight: bold;
            }
            .add-card:hover {
                color: #9b59b6;
            }
            
            .message {
        background-color: #2ecc71; /* Màu xanh lục nhạt */
        color: #ffffff;            /* Màu chữ trắng */
        padding: 15px;             /* Khoảng cách bên trong */
        margin-top: 20px;          /* Khoảng cách phía trên */
        border-radius: 5px;        /* Bo tròn góc */
        text-align: center;        /* Căn giữa nội dung */
        font-weight: bold;         /* Chữ in đậm */
    }
            
        </style>
    </head>
    <body>
        
        <!-- Sidebar -->
        <div class="sidebar">
            <h4>Welcome, <c:out value="${username}" /></h4> <!-- Gọi tên user từ session -->
            <p>Your options:</p>
            <a href="/AssignmentPrj/productionplan/list">List Plan</a>
            <a href="/AssignmentPrj/productionplan/create">Create Plan</a>
            <a href="/AssignmentPrj/product/list">List Product</a>
            <a href="/AssignmentPrj/employee/list">List Employee</a>
            <a href="/AssignmentPrj/productionplan/restore">History Delete Plan</a>
            <a href="logout">Logout</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
            <h2>Dashboard</h2>
            <!-- Pie Chart -->
            <div class="pie-chart"></div>
            <div class="board">
                <!-- Column for Not Implemented Plans -->
                <div class="column">
                    <h3>The plan has NOT been implemented yet (${notImplementedPercentage}%)</h3>
                    <ul class="plan-list">
                        <c:forEach var="plan" items="${notImplemented}">
                            <li>${plan.name}</li>
                        </c:forEach>
                    </ul>
                    <a href="/AssignmentPrj/productionplan/create" class="add-card">+ Add card</a>
                </div>

                <!-- Column for Being Implemented Plans -->
                <div class="column">
                    <h3>The plan is BEING implemented (${beingImplementedPercentage}%)</h3>
                    <ul class="plan-list">
                        <c:forEach var="plan" items="${beingImplemented}">
                            <li>${plan.name}</li>
                        </c:forEach>
                    </ul>
                    <a href="/AssignmentPrj/productionplan/create" class="add-card">+ Add card</a>
                </div>

                <!-- Column for Has Been Implemented Plans -->
                <div class="column">
                    <h3>The plan HAS been implemented (${hasBeenImplementedPercentage}%)</h3>
                    <ul class="plan-list">
                        <c:forEach var="plan" items="${hasBeenImplemented}">
                            <li>${plan.name}</li>
                        </c:forEach>
                    </ul>
                    <a href="/AssignmentPrj/productionplan/create" class="add-card">+ Add card</a>
                </div>
            </div>
        </div>
    </body>
</html>
