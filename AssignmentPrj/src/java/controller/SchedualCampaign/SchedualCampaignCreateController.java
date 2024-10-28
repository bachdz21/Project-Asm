package controller.SchedualCampaign;

import entity.production.SchedualCampaign;
import dal.*;
import controller.accesscontrol.BaseRBACController;
import dal.PlanCampaignDBContext;
import dal.PlanDBContext;
import entity.accesscontrol.User;
import entity.production.PlanCampaign;
import entity.production.Product;
import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SchedualCampaignCreateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int planID = Integer.parseInt(req.getParameter("planID"));

        PlanDBContext planDB = new PlanDBContext();
        PlanCampaignDBContext plaCamDB = new PlanCampaignDBContext();
        SchedualCampaignDBContext schedualCampaign = new SchedualCampaignDBContext();

        List<Date> dates = planDB.getDateByplanID(planID);
        List<PlanCampaign> planCampains = plaCamDB.list(planID);
        for (PlanCampaign planCampain : planCampains) {
            planCampain.getSchedualCampaigns().addAll(schedualCampaign.list(planCampain));
        }

        if (dates != null && !dates.isEmpty() && planCampains != null && !planCampains.isEmpty()) {
            req.setAttribute("dates", dates);
            req.setAttribute("xx", planCampains);
            req.getRequestDispatcher("/view/schedualcampaign/create.jsp").forward(req, resp);
        } else {
            resp.getWriter().println("No dates or products found for the given plan ID.");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        SchedualCampaignDBContext schedualCampaignDB = new SchedualCampaignDBContext();

        List<SchedualCampaign> schedualCampaigns = new ArrayList<>();
        Enumeration<String> parameterNames = req.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            if (paramName.startsWith("quantity_")) {
                String[] parts = paramName.split("_");
                int productId = Integer.parseInt(parts[1]);
                Date date = Date.valueOf(req.getParameter("date_" + productId + "_" + parts[2] + "_" + parts[3]));
                int shift = Integer.parseInt(req.getParameter("shift_" + productId + "_" + parts[2] + "_" + parts[3]));
                int quantity = Integer.parseInt(req.getParameter(paramName));
                int planCampaignId = Integer.parseInt(req.getParameter("planCampaignID_" + productId + "_" + parts[2] + "_" + parts[3]));

                // Create a new SchedualCampaign object
                SchedualCampaign schedualCampaign = new SchedualCampaign();
                schedualCampaign.setPlanCampaign(new PlanCampaign(planCampaignId));
                schedualCampaign.setDate(date);
                schedualCampaign.setShift(shift);
                schedualCampaign.setQuantity(quantity);

                // Add to the list
                schedualCampaigns.add(schedualCampaign);
            }
        }

        // Insert each SchedualCampaign into the database
        for (SchedualCampaign schedualCampaign : schedualCampaigns) {
            schedualCampaignDB.insert(schedualCampaign);
        }

        // Redirect or send response
        resp.sendRedirect("/AssignmentPrj/dashboard.jsp");
    }

    public static void main(String[] args) {
        PlanDBContext planDB = new PlanDBContext();
        List<Date> dates = planDB.getDateByplanID(2);

        List<Product> products = planDB.getProductsByplanID(2); // Đổi từ `xx` thành `products` cho dễ đọc và tránh trùng

        if (products != null && !products.isEmpty()) {
            System.out.println("co data");
        } else {
            System.out.println("no data");
        }
        System.out.println(dates);
        System.out.println(products);
    }
}
