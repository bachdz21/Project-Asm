package controller.productionplan;

import dal.DepartmentDBContext;
import dal.PlanCampaignDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import dal.SchedualCampaignDBContext;
import entity.production.Department;
import entity.production.Plan;
import entity.production.PlanCampaign;
import entity.production.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductionPlanUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planID = Integer.parseInt(request.getParameter("planID"));
        PlanDBContext planDb = new PlanDBContext();
        Plan plan = planDb.get(planID);
        request.setAttribute("plan", plan);
        request.setAttribute("products", new ProductDBContext().list());
        request.setAttribute("depts", new DepartmentDBContext().get("WS"));
        request.getRequestDispatcher("/view/productionplan/update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planID = Integer.parseInt(request.getParameter("planID"));
        PlanDBContext planDb = new PlanDBContext();
        Plan plan = planDb.get(planID);

        // Cập nhật thông tin kế hoạch
        plan.setName(request.getParameter("name"));
        plan.setStart(Date.valueOf(request.getParameter("from")));
        plan.setEnd(Date.valueOf(request.getParameter("to")));

        // Cập nhật Department
        Department dept = new Department();
        dept.setId(Integer.parseInt(request.getParameter("did")));
        plan.setDept(dept);

        // Lấy danh sách ngày cũ và ngày mới
        ArrayList<Date> oldDates = planDb.getDateByplanID(planID);
        Date newStartDate = Date.valueOf(request.getParameter("from"));
        Date newEndDate = Date.valueOf(request.getParameter("to"));
        ArrayList<Date> newDates = getDatesBetween(newStartDate, newEndDate);

        // Xóa các SchedualCampaign không còn tồn tại trong khoảng ngày mới
        oldDates.removeAll(newDates);
        if (!oldDates.isEmpty()) {
            new SchedualCampaignDBContext().deleteByIdPlanAndDate(planID, oldDates);
        }

        // Cập nhật các PlanCampaign mới hoặc hiện tại
        ArrayList<PlanCampaign> campaigns = new ArrayList<>();
        String[] pids = request.getParameterValues("pid");

        for (String pid : pids) {
            PlanCampaign c = new PlanCampaign();
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);

            // Lấy các giá trị quantity và estimate
            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            c.setEstimate(raw_estimate != null && raw_estimate.length() > 0 ? Float.parseFloat(raw_estimate) : 0);

            // Chỉ thêm vào danh sách nếu quantity và estimate > 0
            if (c.getQuantity() > 0 && c.getEstimate() > 0) {
                campaigns.add(c);
            }
        }

        plan.setCampaigns(campaigns);
            planDb.update(plan);
            
            
            
        PlanCampaignDBContext x = new PlanCampaignDBContext();
        ArrayList<Product> productsInPlan = planDb.getProductsByplanID(planID);
        
        List<Integer> productIds = productsInPlan.stream()
                                                 .map(Product::getId)
                                                 .collect(Collectors.toList());
        
        
        for (String pid : pids) {
            PlanCampaign c = new PlanCampaign();
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);

            // Lấy các giá trị quantity và estimate
            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            c.setEstimate(raw_estimate != null && raw_estimate.length() > 0 ? Float.parseFloat(raw_estimate) : 0);

            // Chỉ thêm vào danh sách nếu quantity và estimate > 0
            if (c.getQuantity() > 0 && c.getEstimate() > 0) {
                if(!productIds.contains(p.getId())){
                    x.insert(c);
                }
                else{x.update(c);}
                
            } else{
                x.deleteByPlanIDAndProductID(planID, p.getId());}
        }

            // Chuyển về danh sách kế hoạch với thông báo thành công
            request.getSession().setAttribute("message", "Update successful plan: " + plan.getName() + "!");
            response.sendRedirect("/AssignmentPrj/productionplan/list");
        
    }

    private ArrayList<Date> getDatesBetween(Date startDate, Date endDate) {
        ArrayList<Date> dates = new ArrayList<>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startDate);
        while (!calendar.getTime().after(endDate)) {
            dates.add(new Date(calendar.getTimeInMillis()));
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }
}
