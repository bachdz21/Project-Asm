<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Schedule Campaign</title>
        <style>
            form {
                margin: 0 auto;
            }
            table {
                width: 100%;
            }
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
                                <!-- Shift 1 -->
                                <td>
                                    <input type="text" name="quantity_${campaign.product.id}_${date}_1" placeholder="Enter Quantity"/>
                                    <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_1" value="${campaign.id}"/>
                                    <input type="hidden" name="shift_${campaign.product.id}_${date}_1" value="1"/>
                                    <input type="hidden" name="date_${campaign.product.id}_${date}_1" value="${date}"/>
                                </td>
                                <!-- Shift 2 -->
                                <td>
                                    <input type="text" name="quantity_${campaign.product.id}_${date}_2" placeholder="Enter Quantity"/>
                                    <input type="hidden" name="planCampaignID_${campaign.product.id}_${date}_2" value="${campaign.id}"/>
                                    <input type="hidden" name="shift_${campaign.product.id}_${date}_2" value="2"/>
                                    <input type="hidden" name="date_${campaign.product.id}_${date}_2" value="${date}"/>
                                </td>
                                <!-- Shift 3 -->
                                <td>
                                    <input type="text" name="quantity_${campaign.product.id}_${date}_3" placeholder="Enter Quantity"/>
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
        </form>
        <a href="../dashboard.jsp">Back to Dashboard</a>
    </body>
</html>
