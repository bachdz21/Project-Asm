package controller.productionplancampaign;

import dal.PlanCampaignDBContext;
import entity.production.PlanCampaign;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductionPlanCampaignDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy planId từ tham số yêu cầu
        int planId = Integer.parseInt(request.getParameter("planId"));
        PlanCampaignDBContext db = new PlanCampaignDBContext();
        ArrayList<PlanCampaign> planCampaigns = db.list(planId);

        // Gửi dữ liệu danh sách các PlanCampaign sang trang JSP
        request.setAttribute("planCampaigns", planCampaigns);
        request.getRequestDispatcher("../view/productionplancampaign/detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
