<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Production Plan</title>
        <style>
            /* Global Style */
            body {
                font-family: Arial, sans-serif;
                background-color: #2c3e50;
                color: #ecf0f1;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }
            h2 {
                color: #ecf0f1;
                text-align: center;
                margin-bottom: 20px;
            }
            form {
                background-color: #34495e;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                max-width: 600px;
                width: 100%;
            }
            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }
            input[type="text"],
            input[type="date"],
            select {
                width: calc(100% - 20px);
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #34495e;
                border-radius: 4px;
                background-color: #ecf0f1;
                color: #2c3e50;
                box-sizing: border-box;
            }
            input[type="text"]:focus,
            input[type="date"]:focus,
            select:focus {
                background-color: #ffffff;
                outline: none;
                border-color: #1abc9c;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #34495e;
                color: #ecf0f1;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            th, td {
                padding: 12px;
                text-align: center;
                border: 1px solid #4a6a8b;
            }
            th {
                background-color: #3b4a6b;
            }
            td input[type="text"] {
                width: 90%;
                padding: 8px;
                background-color: #ecf0f1;
                color: #2c3e50;
                border: 1px solid #34495e;
                border-radius: 4px;
            }
            .submit-btn {
                background-color: #1abc9c;
                color: #ffffff;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                margin-top: 15px;
                transition: background-color 0.3s;
                display: block;
                width: 100%;
                text-align: center;
            }
            .submit-btn:hover {
                background-color: #16a085;
            }
            .back-link {
                margin-top: 20px;
                color: #3498db;
                text-decoration: none;
                font-weight: bold;
            }
            .back-link:hover {
                color: #2980b9;
            }
        </style>
        <script>
            function validateForm() {
                const planTitle = document.forms["updateForm"]["name"].value;
                const fromDate = document.forms["updateForm"]["from"].value;
                const toDate = document.forms["updateForm"]["to"].value;
                const workshop = document.forms["updateForm"]["did"].value;

                const from = new Date(fromDate);
                const to = new Date(toDate);

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
                if (from >= to) {
                    alert("The From date must be earlier than the To date");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <h2>Update Production Plan</h2>
        <form name="updateForm" action="update" method="POST" onsubmit="return validateForm()">
            <input type="hidden" name="planID" value="${plan.id}"/>
            <label>Plan Title:</label>
            <input type="text" name="name" value="${plan.name}"/>
            
            <label>From:</label>
            <input type="date" name="from" value="${plan.start}"/> 
            <label>To:</label>
            <input type="date" name="to" value="${plan.end}"/> 
            
            <label>Workshop:</label>
            <select name="did">
                <c:forEach items="${depts}" var="d">
                    <option value="${d.id}" ${plan.dept.id == d.id ? 'selected="selected"' : ''}>${d.name}</option>
                </c:forEach>
            </select>

            <table>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Estimate</th>
                </tr>
                
                <c:forEach items="${products}" var="product">
                    <c:set var="existingCampaign" value="${null}" />
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
            
            <input type="submit" class="submit-btn" value="Save"/>
        </form>
        <a href="/AssignmentPrj/home" class="back-link">Back to Dashboard</a>
    </body>
</html>
