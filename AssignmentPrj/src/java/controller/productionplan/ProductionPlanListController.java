package controller.productionplan;

import entity.production.Plan;
import controller.accesscontrol.BaseRBACController;
import entity.accesscontrol.User;
import dal.PlanDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductionPlanListController extends BaseRBACController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        ArrayList<Plan> plans = db.list();

        // Tạo cấu trúc dữ liệu để nhóm các kế hoạch theo Department Name
        Map<String, List<Plan>> plansByDepartment = new LinkedHashMap<>();
        for (Plan plan : plans) {
            plansByDepartment
                .computeIfAbsent(plan.getDept().getName(), k -> new ArrayList<>())
                .add(plan);
        }

        request.setAttribute("plansByDepartment", plansByDepartment);
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
        ArrayList<Plan> plans = db.list();
        System.out.println(plans.get(0).getCampaigns().size());
    }
    
}
