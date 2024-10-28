/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.productionplancampaign;
import entity.production.PlanCampaign;
import controller.accesscontrol.BaseRBACController;
import dal.PlanCampaignDBContext;
import entity.accesscontrol.User;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductionPlanCampaignListController extends BaseRBACController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanCampaignDBContext db = new PlanCampaignDBContext();
        ArrayList<PlanCampaign> planCampaigns = db.list();
        request.setAttribute("planCampaigns", planCampaigns);
        request.getRequestDispatcher("../view/productionplancampaign/list.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        processRequest(req, resp);
    }
}