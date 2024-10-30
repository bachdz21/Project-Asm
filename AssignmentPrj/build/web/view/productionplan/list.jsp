<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách kế hoạch sản xuất</title>
</head>
<body>
    <h2>Tìm kiếm Kế Hoạch Sản Xuất</h2>

    <!-- Form tìm kiếm kế hoạch sản xuất -->
    <form action="list" method="GET">
        Plan title: <input type="text" name="title" value="${param.title}"/> <br/>

        From: <input type="date" name="from" value="${param.from}"/> 
        To: <input type="date" name="to" value="${param.to}"/> <br/>

        Workshop: 
        <select name="workshop">
            <option value="all">All</option>
            <c:forEach var="department" items="${departments}">
                <option value="${department.id}" 
                        ${param.workshop ne null && param.workshop eq department.id ? "selected=\"selected\"" : ""}>
                    ${department.name}
                </option>
            </c:forEach>
        </select> <br/>

        Product: 
        <select name="product">
            <option value="all">All</option>
            <c:forEach var="product" items="${products}">
                <option value="${product.id}" 
                        ${param.product ne null && param.product eq product.id ? "selected=\"selected\"" : ""}>
                    ${product.name}
                </option>
            </c:forEach>
        </select> <br/>

        <input type="submit" value="Search"/>
    </form>

    <h2>Danh sách Kế Hoạch Sản Xuất</h2>

    <c:if test="${not empty plansByDepartment}">
        <table border="1">
            <thead>
                <tr>
                    <th>Department Name</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Detail</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${plansByDepartment}">
                    <c:set var="departmentName" value="${entry.key}" />
                    <c:set var="plans" value="${entry.value}" />

                    <tr>
                        <td rowspan="${fn:length(plans)}">${departmentName}</td>
                        <td>${plans[0].name}</td>
                        <td>${plans[0].start}</td>
                        <td>${plans[0].end}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/schedualcampaign/create?planID=${plans[0].id}">Detail</a>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/productionplan/delete" method="post" style="display:inline;">
                                <input type="hidden" name="planID" value="${plans[0].id}" />
                                <button type="submit" onclick="return confirm('Bạn có chắc muốn xóa kế hoạch này?')">Delete</button>
                            </form>
                        </td>
                    </tr>

                    <c:forEach var="plan" items="${plans}" varStatus="status">
                        <c:if test="${!status.first}">
                            <tr>
                                <td>${plan.name}</td>
                                <td>${plan.start}</td>
                                <td>${plan.end}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/schedualcampaign/create?planID=${plan.id}">Detail</a>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/productionplan/delete" method="post" style="display:inline;">
                                        <input type="hidden" name="planID" value="${plan.id}" />
                                        <button type="submit" onclick="return confirm('Bạn có chắc muốn xóa kế hoạch này?')">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty plansByDepartment}">
        <p>No plans available.</p>
    </c:if>

    <br>
    <a href="../dashboard.jsp">Quay lại Dashboard</a>
    <c:if test="${not empty param.message}">
        <p style="color: green;">${param.message}</p>
    </c:if>
</body>
</html>
