package controller.productionplan;

import entity.production.Plan;
import controller.accesscontrol.BaseRBACController;
import dal.DepartmentDBContext;
import entity.accesscontrol.User;
import dal.PlanDBContext;
import dal.ProductDBContext;
import entity.production.Department;
import entity.production.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.util.Comparator;

public class ProductionPlanListController extends BaseRBACController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String raw_planName = request.getParameter("planName");
        String raw_date = request.getParameter("date");
        String raw_workshop = request.getParameter("workshop");
        String raw_product = request.getParameter("product");

        String planName = raw_planName != null ? raw_planName.trim() : null;
        Date date = (raw_date != null && !raw_date.isBlank()) ? Date.valueOf(raw_date) : null;
        Integer workshopId = (raw_workshop != null && !raw_workshop.equals("-1")) ? Integer.parseInt(raw_workshop) : null;
        Integer productId = (raw_product != null && !raw_product.equals("-1")) ? Integer.parseInt(raw_product) : null;

        PlanDBContext dbPlan = new PlanDBContext();
        ArrayList<Plan> plans = dbPlan.searchPlan(planName, date, workshopId, productId);

        plans.sort(Comparator
        .comparing((Plan plan) -> plan.getDept().getName())          // Tên department
        .thenComparing(Plan::getStart)                           // Start date
        .thenComparing(Plan::getEnd)                             // End date
        .thenComparing(Plan::getName)                                // Plan name
        .thenComparing(plan -> plan.getCampaigns().size()));        // Product name
        // Nhóm các kế hoạch theo Workshop
        Map<String, List<Plan>> plansByWorkshop = new LinkedHashMap<>();
        for (Plan plan : plans) {
            String workshopName = plan.getDept().getName();
            plansByWorkshop.computeIfAbsent(workshopName, k -> new ArrayList<>()).add(plan);
        }

        request.setAttribute("plansByWorkshop", plansByWorkshop);

        // Lấy danh sách sản phẩm và workshop cho dropdown
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();
        request.setAttribute("products", dbProduct.list());
        request.setAttribute("workshops", dbDepts.get("WS"));
        request.getRequestDispatcher("../view/productionplan/list.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public static void main(String[] args) {
        PlanDBContext db = new PlanDBContext();
        ArrayList<Plan> plans = db.searchPlan(null, null, null, null);
        System.out.println(plans.size());

    }

}
