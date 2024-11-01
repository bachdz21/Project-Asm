package controller.accesscontrol;

import dal.UserDBContext;
import entity.accesscontrol.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String user = req.getParameter("username");
    String pass = req.getParameter("password");

    UserDBContext db = new UserDBContext();
    User account = db.get(user, pass);

    if (account != null) {
        // Set session attribute
        req.getSession().setAttribute("username", account.getUsername()); // Lưu tên người dùng vào session
        req.getSession().setAttribute("account", account);

        // Redirect to the dashboard page
        resp.sendRedirect("/AssignmentPrj/home");
    } else {
        // Set error message attribute and forward to login page
        req.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //pre-processing
        req.getRequestDispatcher("login.jsp").forward(req, resp);
        //post-processing
    }

}
