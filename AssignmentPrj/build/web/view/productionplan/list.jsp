<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tìm kiếm Kế Hoạch Sản Xuất</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #1e2a38;
                color: #ecf0f1;
                margin: 0;
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            h2, h3 {
                color: #ecf0f1;
                margin-bottom: 15px;
                text-align: center;
            }

            form {
                background-color: #2c3e50;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
                width: 100%;
                max-width: 600px;
                margin-bottom: 20px;
            }

            form input[type="text"], form input[type="date"], form select {
                width: 100%;
                padding: 8px;
                margin-bottom: 15px;
                border: none;
                border-radius: 5px;
                background-color: #34495e;
                color: #ecf0f1;
            }

            form input[type="submit"] {
                background-color: #3498db;
                color: #ffffff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
                width: 100%;
            }

            form input[type="submit"]:hover {
                background-color: #2980b9;
            }

            table {
                width: 100%;
                max-width: 800px;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: #2c3e50;
                border-radius: 8px;
                overflow: hidden;
            }

            table th, table td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #34495e;
                color: #ecf0f1;
            }

            table th {
                background-color: #34495e;
                font-weight: bold;
            }

            table tr:hover {
                background-color: #3b4a6b;
            }

            .message {
                background-color: green;
                padding: 10px;
                border-radius: 5px;
                text-align: center;
                margin-bottom: 15px;
                color: #ffffff;
                width: 100%;
                max-width: 600px;
            }

            .table-links a {
                color: #1abc9c;
                text-decoration: none;
                font-weight: bold;
                display: inline-block;
                padding: 5px 10px;
                border-radius: 4px;
                transition: background-color 0.3s ease;
            }

            .table-links a:hover {
                background-color: #16a085;
                color: #ecf0f1;
            }

            .back-link {
                background-color: #3498db;
                color: #ffffff;
                padding: 10px 20px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                margin-top: 15px;
            }

            .back-link:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <h2>Search for Production Plan Plan </h2>

        <form action="list" method="GET">
            Plan Name: <input type="text" name="planName" value="${param.planName}"/> <br/>
            Date: <input type="date" name="date" value="${param.date}"/> <br/>

            Workshop: 
            <select name="workshop">
                <option value="-1">All</option>
                <c:forEach items="${workshops}" var="workshop">
                    <option value="${workshop.id}" 
                            ${param.workshop ne null && param.workshop eq workshop.id ? "selected=\"selected\"" : ""}>
                        ${workshop.name}
                    </option>
                </c:forEach>
            </select> <br/>

            Product: 
            <select name="product">
                <option value="-1">All</option>
                <c:forEach items="${products}" var="product">
                    <option value="${product.id}" 
                            ${param.product ne null && param.product eq product.id ? "selected=\"selected\"" : ""}>
                        ${product.name}
                    </option>
                </c:forEach>
            </select> <br/>

            <input type="submit" value="Search"/>
        </form>
            <c:if test="${empty plansByWorkshop}">
            <p class="message">No plans available.</p>
        </c:if>

        <c:if test="${not empty sessionScope.message}">
            <p class="message">${sessionScope.message}</p>
            <c:remove var="message" scope="session" />
        </c:if>


        <h2>Production Plan List</h2>

        <c:forEach items="${plansByWorkshop}" var="entry">
            <c:set var="workshopName" value="${entry.key}" />
            <c:set var="plans" value="${entry.value}" />

            <h3>Workshop: ${workshopName}</h3>
            <table>
                <tr>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Product Name</th>
                    <th>Detail</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${plans}" var="plan">
                    <tr>
                        <td>${plan.name}</td>
                        <td>${plan.start}</td>
                        <td>${plan.end}</td>
                        <td>
                            <c:forEach items="${plan.campaigns}" var="campaign">
                                ${campaign.product.name}<br/>
                            </c:forEach>
                        </td>
                        <td class="table-links">
                            <a href="${pageContext.request.contextPath}/schedualcampaign/create?planID=${plan.id}">Detail</a>
                        </td>
                        <td class="table-links">
                            <a href="${pageContext.request.contextPath}/productionplan/update?planID=${plan.id}">Edit</a>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/productionplan/delete" method="post" style="display:inline;">
                                <input type="hidden" name="planID" value="${plan.id}" />
                                <button type="submit" onclick="return confirm('Bạn có chắc muốn xóa kế hoạch này?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:forEach>

        

        <a href="/AssignmentPrj/home" class="back-link">Back to Dashboard</a>
    </body>
</html>
