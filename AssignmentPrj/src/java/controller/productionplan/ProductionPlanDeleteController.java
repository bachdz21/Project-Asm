/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import entity.production.*;
import controller.accesscontrol.BaseRBACController;
import dal.PlanDBContext;
import entity.accesscontrol.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class ProductionPlanDeleteController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int planID = Integer.parseInt(req.getParameter("planID"));
        PlanDBContext planDB = new PlanDBContext();

        // Gọi phương thức delete (xóa mềm)
        planDB.delete(planID);

        // Chuyển hướng về trang list.jsp với thông báo thành công
        resp.sendRedirect(req.getContextPath() + "/productionplan/list?message=Plan deleted successfully");
    }
}
