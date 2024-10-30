/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.PlanDBContext;
import entity.accesscontrol.User;
import entity.production.Plan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductionPlanRestoreController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> deletedPlans = planDB.listPlanIsDelete();

        // Gửi danh sách kế hoạch đã xóa tới trang listIsDelete.jsp
        req.setAttribute("deletedPlans", deletedPlans);
        req.getRequestDispatcher("/view/productionplan/listIsDelete.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int planID = Integer.parseInt(req.getParameter("planID"));
        PlanDBContext planDB = new PlanDBContext();

        // Khôi phục kế hoạch
        planDB.restorePlanByPlanID(planID);

        // Chuyển hướng về trang listIsDelete.jsp với thông báo
        resp.sendRedirect(req.getContextPath() + "/productionplan/restore?message=Plan restored successfully");
    }
   
   
}
