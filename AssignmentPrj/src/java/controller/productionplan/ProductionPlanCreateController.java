/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.*;
import entity.accesscontrol.User;
import entity.production.Department;
import entity.production.Plan;
import entity.production.PlanCampaign;
import entity.production.Product;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

public class ProductionPlanCreateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();

        request.setAttribute("products", dbProduct.list());
        request.setAttribute("depts", dbDepts.get("WS"));

        request.getRequestDispatcher("../view/productionplan/create.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        String title = request.getParameter("name");
        String fromDate = request.getParameter("from");
        String toDate = request.getParameter("to");
        String workshop = request.getParameter("did");

        

         {
            // Không có lỗi -> tiến hành tạo kế hoạch sản xuất
            Plan plan = new Plan();
            plan.setName(title);
            plan.setStart(Date.valueOf(fromDate));
            plan.setEnd(Date.valueOf(toDate));

            Department d = new Department();
            d.setId(Integer.parseInt(workshop));
            plan.setDept(d);

            String[] pids = request.getParameterValues("pid");
            for (String pid : pids) {
                PlanCampaign c = new PlanCampaign();

                Product p = new Product();
                p.setId(Integer.parseInt(pid));
                c.setProduct(p);
                c.setPlan(plan);

                String raw_quantity = request.getParameter("quantity" + pid);
                String raw_estimate = request.getParameter("estimate" + pid);

                c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
                c.setEstimate(raw_estimate != null && raw_estimate.length() > 0 ? Float.parseFloat(raw_estimate) : 0);

                if (c.getQuantity() > 0 && c.getEstimate() > 0) {
                    plan.getCampaigns().add(c);
                }
            }

            if (plan.getCampaigns().size() > 0) {
                PlanDBContext db = new PlanDBContext();
                db.insert(plan);

                // Set thông báo thành công và chuyển hướng về dashboard
                request.getSession().setAttribute("message", "Create successful plan: " + plan.getName()+"!");
                response.sendRedirect("/AssignmentPrj/dashboard.jsp");
            } else {
                request.getSession().setAttribute("message", "Create unsuccessful. Your plan does not have any products/campaigns!");
                response.sendRedirect("/AssignmentPrj/dashboard.jsp");
                
            }
        }
    }

}
