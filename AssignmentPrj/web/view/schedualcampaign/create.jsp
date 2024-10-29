<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Schedule Campaign</title>
        <style>

            th, td {
                padding: 8px;
                text-align: center;
            }
            input[type="text"] {
                width: 100%;
            }
        </style>
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
        <h2>Create Schedule for Campaign</h2>
        <form action="create" method="post">
            <table border="1">
                <thead>
                    <tr>
                        <th rowspan="2">Product</th>
                            <c:forEach var="date" items="${dates}">
                            <th colspan="3">${date}</th>
                            </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach var="date" items="${dates}">
                            <th>K1</th>
                            <th>K2</th>
                            <th>K3</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="campaign" items="${xx}">
                        <tr>
                            <td>${campaign.product.name}</td>
                            <c:forEach var="date" items="${dates}">
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

                                <!-- Shift 1 -->
                                <td>
                                    <input type="text" name="quantity_${campaign.product.id}_${date}_1" 
                                           value="${foundQuantityK1}" placeholder="Enter Quantity"/>
                                    <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_1" value="${campaign.id}"/>
                                    <input type="hidden" name="shift_${campaign.product.id}_${date}_1" value="1"/>
                                    <input type="hidden" name="date_${campaign.product.id}_${date}_1" value="${date}"/>
                                </td>

                                <!-- Shift 2 -->
                                <td>
                                    <input type="text" name="quantity_${campaign.product.id}_${date}_2" 
                                           value="${foundQuantityK2}" placeholder="Enter Quantity"/>
                                    <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_2" value="${campaign.id}"/>
                                    <input type="hidden" name="shift_${campaign.product.id}_${date}_2" value="2"/>
                                    <input type="hidden" name="date_${campaign.product.id}_${date}_2" value="${date}"/>
                                </td>

                                <!-- Shift 3 -->
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
            <br>
            <input type="submit" value="Submit"/>
            <!-- Kiểm tra và hiển thị thông báo -->
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
        </form>
        <a href="../dashboard.jsp">Back to Dashboard</a>
    </body>
</html>
