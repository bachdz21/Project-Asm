package controller.accesscontrol;

import dal.PlanDBContext;
import dal.UserDBContext;
import entity.accesscontrol.User;
import entity.production.Plan;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Home extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        List<Plan> allPlans = db.list();
        String username = (String) req.getSession().getAttribute("username");
             req.setAttribute("username", username);
        LocalDate currentDate = LocalDate.now();

        List<Plan> notImplemented = new ArrayList<>();
        List<Plan> beingImplemented = new ArrayList<>();
        List<Plan> hasBeenImplemented = new ArrayList<>();

        for (Plan plan : allPlans) {
            LocalDate startDate = plan.getStart().toLocalDate();
            LocalDate endDate = plan.getEnd().toLocalDate();
            if (startDate.isAfter(currentDate)) {
                notImplemented.add(plan);
            } else if (endDate.isBefore(currentDate)) {
                hasBeenImplemented.add(plan);
            } else {
                beingImplemented.add(plan);
            }
        }

        int totalPlans = allPlans.size();
        int notImplementedPercentage = (int) ((double) notImplemented.size() / totalPlans * 100);
        int beingImplementedPercentage = (int) ((double) beingImplemented.size() / totalPlans * 100);
        int hasBeenImplementedPercentage = (int) ((double) hasBeenImplemented.size() / totalPlans * 100);

        // Gửi danh sách các kế hoạch phân loại đến trang JSP
        req.setAttribute("notImplemented", notImplemented);
        req.setAttribute("beingImplemented", beingImplemented);
        req.setAttribute("hasBeenImplemented", hasBeenImplemented);
        req.setAttribute("notImplementedPercentage", notImplementedPercentage);
        req.setAttribute("beingImplementedPercentage", beingImplementedPercentage);
        req.setAttribute("hasBeenImplementedPercentage", hasBeenImplementedPercentage);

        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
