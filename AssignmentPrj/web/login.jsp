<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        /* Đặt nền tối và kiểu chữ chung */
        body {
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            color: #ecf0f1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        h2 {
            color: #ecf0f1;
            margin-bottom: 20px;
        }

        /* Kiểu cho form đăng nhập */
        form {
            background-color: #34495e;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 300px;
            display: flex;
            flex-direction: column;
            margin-bottom: 100px;
        }

        label {
            margin-bottom: 8px;
            color: #ecf0f1;
        }

        input[type="text"],
        input[type="password"] {
            /*width: 100%;*/
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #34495e;
            border-radius: 4px;
            background-color: #ecf0f1;
            color: #2c3e50;
        }

        input[type="submit"] {
            background-color: #1abc9c;
            color: #ffffff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #16a085;
        }

        /* Thông báo lỗi */
        p {
            color: #e74c3c;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>Đăng nhập</h2>
    
    <!-- Hiển thị thông báo lỗi nếu có -->
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p><%= errorMessage %></p>
    <%
        }
    %>
    
    <form action="login" method="post">
        <label for="username">Tên đăng nhập:</label>
        <input type="text" id="username" name="username">
        
        <label for="password">Mật khẩu:</label>
        <input type="password" id="password" name="password">
        
        <input type="submit" value="Đăng nhập">
    </form>
</body>
</html>
