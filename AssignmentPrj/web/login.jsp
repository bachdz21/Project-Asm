<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Đăng nhập</h2>
    
    <!-- Hiển thị thông báo lỗi nếu có -->
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p style="color: red;"><%= errorMessage %></p>
    <%
        }
    %>
    
    <form action="login" method="post">
        <label for="username">Tên đăng nhập:</label><br>
        <input type="text" id="username" name="username"><br><br>
        
        <label for="password">Mật khẩu:</label><br>
        <input type="password" id="password" name="password"><br><br>
        
        <input type="submit" value="Đăng nhập">
    </form>
</body>
</html>
