package cnpm.ergo.controller.Admin.Statics;

import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.Product;
import cnpm.ergo.entity.ProductType;
import cnpm.ergo.service.implement.OrderServiceImpl;
import cnpm.ergo.service.implement.ProductServiceImpl;
import cnpm.ergo.service.implement.ProductTypeServiceImpl;
import cnpm.ergo.service.interfaces.IOrderService;
import cnpm.ergo.service.interfaces.IProductTypeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@WebServlet(urlPatterns = "/admin/statistic")
public class StaticController extends HttpServlet {
    
    private IOrderService orderService = new OrderServiceImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();
    private IProductTypeService productTypeService = new ProductTypeServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if admin is logged in
        if (req.getSession().getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            List<Order> allOrders = orderService.findAll();
            List<Product> allProducts = productService.findAllProduct();
            List<ProductType> allProductTypes = productTypeService.getAllProductTypes();
            
            // Calculate overall statistics
            double totalRevenue = 0;
            double totalProfit = 0;
            int totalOrders = allOrders.size();
            int deliveredOrders = 0;
            int processingOrders = 0;
            int pendingOrders = 0;
            int cancelledOrders = 0;
            
            // Monthly revenue for chart (last 6 months)
            Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
            Map<String, Integer> monthlyOrderCount = new LinkedHashMap<>();
            
            // Initialize last 6 months
            LocalDate now = LocalDate.now();
            for (int i = 5; i >= 0; i--) {
                YearMonth ym = YearMonth.from(now.minusMonths(i));
                String key = ym.getYear() + "-" + String.format("%02d", ym.getMonthValue());
                monthlyRevenue.put(key, 0.0);
                monthlyOrderCount.put(key, 0);
            }
            
            // Process orders
            for (Order order : allOrders) {
                if (order.getStatus() != null) {
                    switch (order.getStatus().toLowerCase()) {
                        case "delivered":
                            deliveredOrders++;
                            totalRevenue += order.getActualCost();
                            totalProfit += order.getActualCost() * 0.2; // Assume 20% profit margin
                            break;
                        case "processing":
                            processingOrders++;
                            break;
                        case "pending":
                            pendingOrders++;
                            break;
                        case "cancelled":
                            cancelledOrders++;
                            break;
                    }
                }
                
                // Monthly breakdown
                if (order.getOrderDate() != null) {
                    LocalDate orderDate = order.getOrderDate().toLocalDate();
                    String monthKey = orderDate.getYear() + "-" + String.format("%02d", orderDate.getMonthValue());
                    if (monthlyRevenue.containsKey(monthKey)) {
                        if ("delivered".equalsIgnoreCase(order.getStatus())) {
                            monthlyRevenue.put(monthKey, monthlyRevenue.get(monthKey) + order.getActualCost());
                        }
                        monthlyOrderCount.put(monthKey, monthlyOrderCount.get(monthKey) + 1);
                    }
                }
            }
            
            // Product statistics
            int totalProducts = allProducts.size();
            int totalProductTypes = allProductTypes.size();
            int lowStockCount = 0;
            int outOfStockCount = 0;
            
            // Top selling products (by quantity in stock - inverse as proxy)
            List<Map<String, Object>> productStats = new ArrayList<>();
            for (ProductType pt : allProductTypes) {
                if (pt.getQuantity() <= 0) {
                    outOfStockCount++;
                } else if (pt.getQuantity() < 10) {
                    lowStockCount++;
                }
                
                Map<String, Object> stat = new HashMap<>();
                stat.put("typeId", pt.getTypeId());
                stat.put("productName", pt.getProduct() != null ? pt.getProduct().getName() : "Unknown");
                stat.put("color", pt.getColor());
                stat.put("price", pt.getPrice());
                stat.put("quantity", pt.getQuantity());
                stat.put("material", pt.getMaterial());
                productStats.add(stat);
            }
            
            // Sort by quantity ascending (low stock first)
            productStats.sort((a, b) -> Integer.compare((Integer) a.get("quantity"), (Integer) b.get("quantity")));
            
            // Category distribution
            Map<String, Integer> categoryDistribution = new LinkedHashMap<>();
            for (Product p : allProducts) {
                if (p.getCategory() != null) {
                    String catName = p.getCategory().getCategoryName();
                    categoryDistribution.put(catName, categoryDistribution.getOrDefault(catName, 0) + 1);
                }
            }
            
            // Set attributes for JSP
            req.setAttribute("totalRevenue", totalRevenue);
            req.setAttribute("totalProfit", totalProfit);
            req.setAttribute("totalOrders", totalOrders);
            req.setAttribute("deliveredOrders", deliveredOrders);
            req.setAttribute("processingOrders", processingOrders);
            req.setAttribute("pendingOrders", pendingOrders);
            req.setAttribute("cancelledOrders", cancelledOrders);
            
            req.setAttribute("totalProducts", totalProducts);
            req.setAttribute("totalProductTypes", totalProductTypes);
            req.setAttribute("lowStockCount", lowStockCount);
            req.setAttribute("outOfStockCount", outOfStockCount);
            
            req.setAttribute("monthlyRevenue", monthlyRevenue);
            req.setAttribute("monthlyOrderCount", monthlyOrderCount);
            req.setAttribute("categoryDistribution", categoryDistribution);
            req.setAttribute("productStats", productStats);
            
            // Convert maps to JSON strings for JavaScript charts
            StringBuilder revenueLabels = new StringBuilder("[");
            StringBuilder revenueData = new StringBuilder("[");
            StringBuilder orderCountData = new StringBuilder("[");
            boolean first = true;
            for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
                if (!first) {
                    revenueLabels.append(",");
                    revenueData.append(",");
                    orderCountData.append(",");
                }
                revenueLabels.append("'").append(entry.getKey()).append("'");
                revenueData.append(entry.getValue());
                orderCountData.append(monthlyOrderCount.get(entry.getKey()));
                first = false;
            }
            revenueLabels.append("]");
            revenueData.append("]");
            orderCountData.append("]");
            
            req.setAttribute("revenueLabelsJson", revenueLabels.toString());
            req.setAttribute("revenueDataJson", revenueData.toString());
            req.setAttribute("orderCountDataJson", orderCountData.toString());
            
            // Category chart data
            StringBuilder catLabels = new StringBuilder("[");
            StringBuilder catData = new StringBuilder("[");
            first = true;
            for (Map.Entry<String, Integer> entry : categoryDistribution.entrySet()) {
                if (!first) {
                    catLabels.append(",");
                    catData.append(",");
                }
                catLabels.append("'").append(entry.getKey()).append("'");
                catData.append(entry.getValue());
                first = false;
            }
            catLabels.append("]");
            catData.append("]");
            
            req.setAttribute("categoryLabelsJson", catLabels.toString());
            req.setAttribute("categoryDataJson", catData.toString());
            
            req.getRequestDispatcher("views/statistic.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Failed to load statistics: " + e.getMessage());
            req.getRequestDispatcher("views/statistic.jsp").forward(req, resp);
        }
    }
}
