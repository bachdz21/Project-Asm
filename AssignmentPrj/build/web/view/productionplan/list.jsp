<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tìm kiếm Kế Hoạch Sản Xuất</title>
    </head>
    <body>
        <h2>Tìm kiếm Kế Hoạch Sản Xuất</h2>

        <!-- Form tìm kiếm kế hoạch sản xuất -->
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

        <h2>Danh sách Kế Hoạch Sản Xuất</h2>

        <!-- Hiển thị bảng kế hoạch cho từng Workshop -->
        <c:forEach items="${plansByWorkshop}" var="entry">
            <c:set var="workshopName" value="${entry.key}" />
            <c:set var="plans" value="${entry.value}" />

            <h3>Workshop: ${workshopName}</h3>
            <table border="1">
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
                        <td>
                            <a href="${pageContext.request.contextPath}/schedualcampaign/create?planID=${plan.id}">Detail</a>
                        </td>
                        <td>   
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


        <c:if test="${empty plansByWorkshop}">
            <p>No plans available.</p>
        </c:if>

        <c:if test="${not empty sessionScope.message}">
            <p class="message">${sessionScope.message}</p>
            <c:remove var="message" scope="session" />
        </c:if>

        <br>
        <a href="../dashboard.jsp">Back to Dashboard</a>
    </body>
</html>