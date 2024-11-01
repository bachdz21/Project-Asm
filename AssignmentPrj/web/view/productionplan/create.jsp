<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Production Plan</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            color: #ecf0f1;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        
        .container {
            margin-top: 100px;
            width: 100%;
            max-width: 600px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .form-section {
            background-color: #34495e;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            margin-bottom: 20px;
            width: 100%;
            max-width: 500px;
        }

        h2 {
            color: #ecf0f1;
            text-align: center;
            margin: 0;
            padding: 10px 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #34495e;
        }

        th {
            background-color: #34495e;
            color: #ecf0f1;
        }

        td input[type="text"], 
        td input[type="date"], 
        select, 
        input[type="text"], 
        input[type="date"] {
            width: 90%;
            padding: 8px;
            border: 1px solid #34495e;
            border-radius: 4px;
            background-color: #ecf0f1;
            color: #2c3e50;
        }

        td input[type="text"]:focus, 
        td input[type="date"]:focus, 
        select:focus {
            background-color: #ffffff;
            outline: none;
            border-color: #1abc9c;
        }

        .submit-btn {
            background-color: #1abc9c;
            color: #ffffff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
            display: block;
            width: 100%;
        }

        .submit-btn:hover {
            background-color: #16a085;
        }

        .back-link {
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
            display: block;
            margin-top: 15px;
            text-align: center;
        }

        .back-link:hover {
            color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Production Plan</h2>
        <div class="form-section">
            <form name="productionPlanForm" action="create" method="POST" onsubmit="return validateForm()">
                Plan title:<br/><br/> <input type="text" name="name"/> <br/><br/>
                From: <br/><br/><input type="date" name="from"/> <br/><br/>
                To:<br/><br/> <input type="date" name="to"/> <br/><br/>
                Workshop:<br/><br/> 
                <select name="did">
                    <option value="-1">Select Workshop</option>
                    <c:forEach items="${requestScope.depts}" var="d">
                        <option value="${d.id}">${d.name}</option>
                    </c:forEach>
                </select> <br/><br/>
                <table>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Estimate</th>
                    </tr>
                    <c:forEach items="${requestScope.products}" var="p">
                        <tr>
                            <td>${p.name}<input type="hidden" value="${p.id}" name="pid"/></td>
                            <td><input type="text" name="quantity${p.id}"/></td>
                            <td><input type="text" name="estimate${p.id}"/></td>
                        </tr>   
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" class="submit-btn" value="Create"/>
            </form>
            <a href="/AssignmentPrj/home" class="back-link">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
