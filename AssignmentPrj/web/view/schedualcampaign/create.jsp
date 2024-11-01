<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Campaign</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            color: #ecf0f1;
            display: flex;
            justify-content: center;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #ecf0f1;
            margin-bottom: 20px;
        }

        .form-container {
            width: 100%;
            max-width: 900px;
            background-color: #34495e;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #4a6a8b;
            text-align: center;
        }

        th:first-child, td:first-child {
            width: 180px; /* Tăng độ rộng cột Date */
        }

        th {
            background-color: #3b4a6b;
            color: #ecf0f1;
        }

        td {
            background-color: #f1f1f1;
            color: #2c3e50;
        }

        td input[type="text"] {
            width: 75%;
            padding: 8px;
            border: 1px solid #34495e;
            border-radius: 4px;
            background-color: #ecf0f1;
            color: #2c3e50;
        }

        td input[type="text"]:focus {
            background-color: #ffffff;
            outline: none;
            border-color: #1abc9c;
        }

        .submit-btn {
            display: block;
            width: 100%;
            max-width: 200px;
            padding: 10px;
            margin: 20px auto;
            background-color: #1abc9c;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
            text-align: center;
        }

        .submit-btn:hover {
            background-color: #16a085;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #3498db;
            text-decoration: none;
            font-weight: bold;
        }

        .back-link:hover {
            color: #2980b9;
        }

        .message {
            background-color: #27ae60;
            color: #ffffff;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Chi tiết Kế Hoạch Sản Xuất</h2>

        <form action="create" method="POST">
            <c:if test="${not empty planCampaigns}">
                <table>
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
            </c:if><br>


            <c:if test="${not empty planCampaigns}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Date</th>
                                <c:forEach var="campaign" items="${xx}">
                                <th colspan="3">${campaign.product.name}</th>
                                </c:forEach>
                        </tr>
                        <tr>
                            <th></th>
                                <c:forEach var="campaign" items="${xx}">
                                <th>K1</th>
                                <th>K2</th>
                                <th>K3</th>
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Lặp qua từng ngày -->
                        <c:forEach var="date" items="${dates}">
                            <tr>
                                <td>${date}</td>

                                <!-- Lặp qua từng sản phẩm -->
                                <c:forEach var="campaign" items="${xx}">
                                    <c:set var="foundQuantityK1" value="" />
                                    <c:set var="foundQuantityK2" value="" />
                                    <c:set var="foundQuantityK3" value="" />

                                    <!-- Tìm và hiển thị Quantity cho mỗi ca -->
                                    <c:forEach var="schedual" items="${campaign.schedualCampaigns}">
                                        <c:if test="${schedual.date == date}">
                                            <c:choose>
                                                <c:when test="${schedual.shift == 1}">
                                                    <c:set var="foundQuantityK1" value="${schedual.quantity}" />
                                                </c:when>
                                                <c:when test="${schedual.shift == 2}">
                                                    <c:set var="foundQuantityK2" value="${schedual.quantity}" />
                                                </c:when>
                                                <c:when test="${schedual.shift == 3}">
                                                    <c:set var="foundQuantityK3" value="${schedual.quantity}" />
                                                </c:when>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>

                                    <!-- Ô nhập liệu cho mỗi ca -->
                                    <td>
                                        <input type="text" name="quantity_${campaign.product.id}_${date}_1" 
                                               value="${foundQuantityK1}" placeholder="Enter Quantity"/>
                                        <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_1" value="${campaign.id}"/>
                                        <input type="hidden" name="shift_${campaign.product.id}_${date}_1" value="1"/>
                                        <input type="hidden" name="date_${campaign.product.id}_${date}_1" value="${date}"/>
                                    </td>

                                    <td>
                                        <input type="text" name="quantity_${campaign.product.id}_${date}_2" 
                                               value="${foundQuantityK2}" placeholder="Enter Quantity"/>
                                        <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_2" value="${campaign.id}"/>
                                        <input type="hidden" name="shift_${campaign.product.id}_${date}_2" value="2"/>
                                        <input type="hidden" name="date_${campaign.product.id}_${date}_2" value="${date}"/>
                                    </td>

                                    <td>
                                        <input type="text" name="quantity_${campaign.product.id}_${date}_3" 
                                               value="${foundQuantityK3}" placeholder="Enter Quantity"/>
                                        <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_3" value="${campaign.id}"/>
                                        <input type="hidden" name="shift_${campaign.product.id}_${date}_3" value="3"/>
                                        <input type="hidden" name="date_${campaign.product.id}_${date}_3" value="${date}"/>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <br>
            
            <input type="submit" class="submit-btn" value="Submit"/>

            
        </form>
        
        <a href="/AssignmentPrj/home" class="back-link">Back to Dashboard</a>
    </div>
</body>
</html>
