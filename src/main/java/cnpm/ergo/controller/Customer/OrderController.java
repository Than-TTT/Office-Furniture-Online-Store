package cnpm.ergo.controller.Customer;

import java.io.IOException;
import java.util.List;

import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.OrderItem;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherDto;
import cnpm.ergo.service.interfaces.IOrderItemService;
import cnpm.ergo.service.interfaces.IOrderService;
import cnpm.ergo.service.interfaces.IVoucherService;
import cnpm.ergo.service.implement.OrderItemServiceImpl;
import cnpm.ergo.service.implement.OrderServiceImpl;
import cnpm.ergo.service.implement.VoucherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, 
                 maxFileSize = 1024 * 1024 * 5, 
                 maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/customer/order", "/customer/order/voucher", "/customer/order/shippingInfo", "/customer/order/paymentMethod", "/customer/confirmOrder" } )
public class OrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IOrderService orderService;
    private IOrderItemService orderItemService;
    private IVoucherService voucherService;
    private List<VoucherDto> listVoucherCanApply;

    @Override
    public void init() throws ServletException {
        this.orderService = new OrderServiceImpl();
        this.orderItemService = new OrderItemServiceImpl();
        this.voucherService = new VoucherServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String url = req.getRequestURI();
    	if (url.endsWith("/customer/order")) {
    		try {
    			//Order
                int orderId = 1; 
                Order order = orderService.findById(orderId);
                
                //Order Item
                List<OrderItem> orderItems = orderItemService.findAll(orderId);
                if (orderItems != null && !orderItems.isEmpty()) {
                    req.setAttribute("orderItems", orderItems);
                } else {
                    req.setAttribute("orderItems", "No items found for this order");
                }

                //List voucher có thể áp cho order
				List<VoucherDto> listVoucherCanApply = voucherService.voucherByPriceForOder(order);
				List<VoucherDto> listVoucherByProduct = voucherService.voucherByProductForOder(order);
				listVoucherCanApply.addAll(listVoucherByProduct);
				
				//List voucher by price chưa đủ đk áp cho order
				List<VoucherDto> listVoucherCanNotApply = voucherService.voucherByPriceNotForOder(order);
				
                if (order == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                    return;
                }
                
                //Voucher
                String selectedVoucher = null;
                if (req.getParameter("selectedVoucher") != null) {
                	int voucherId = Integer.parseInt(req.getParameter("selectedVoucher"));
                	boolean isVoucherByProduct = listVoucherByProduct.stream()
                		    .anyMatch(voucher -> voucher.getVoucherId() == voucherId);
                	
                	req.setAttribute("isVoucherByProduct", isVoucherByProduct);
                	
                    Voucher voucher = voucherService.findById(voucherId);
                    selectedVoucher = voucher.getCode();
                    
                    //Voucher By Price
                    if (voucher != null && isVoucherByProduct != true) {
                    	order.setDiscount(voucher.getDiscount());
                    	order.setVoucher(voucher);
                    	order.setActualCost(order.getTotalCost() * (1 - order.getDiscount()) + 30000);
                    	orderService.update(order);
                    	req.setAttribute("voucherId", voucher.getVoucherId());
                    	req.setAttribute("sale", order.getTotalCost() * order.getDiscount());
                    }
                   //Voucher By Product
                    else if (voucher != null && isVoucherByProduct == true) {
                    	order.setDiscount(voucher.getDiscount());
                    	order.setVoucher(voucher);
                    	double sale = voucherService.CountDiscountPrice(orderItems, voucher);
                    	order.setActualCost(order.getTotalCost() - sale + 30000);
                    	orderService.update(order);
                    	req.setAttribute("voucherId", voucher.getVoucherId());
                    	req.setAttribute("sale", sale);
                    }
                    else {
                    	order.setDiscount(0);
                    	order.setVoucher(null);
                    	order.setActualCost(order.getTotalCost() + 30000);
                    	orderService.update(order);
                    }
                    // xử lý lỗi khi người dùng xóa ?selectedVoucher =  rồi reload
                } else {
                	Voucher v = order.getVoucher();
                	if (v != null) {
                		selectedVoucher = v.getCode();
                		boolean isVoucherByProduct = listVoucherByProduct.stream()
                    		    .anyMatch(voucher -> voucher.getVoucherId() == v.getVoucherId());
                    	
                    	req.setAttribute("isVoucherByProduct", isVoucherByProduct);
                    	
                    	if (isVoucherByProduct != true) {
                    		double sale = order.getTotalCost() * order.getDiscount();
                    		req.setAttribute("voucherId", v.getVoucherId());
                        	req.setAttribute("sale", sale);
                    	}
                    	else {
                    		double sale = voucherService.CountDiscountPrice(orderItems, v);
                    		req.setAttribute("voucherId", v.getVoucherId());
                        	req.setAttribute("sale", sale);
                    	}
                	}
                }    
                if (req.getParameter("error") != null) {
                	String error = req.getParameter("error");
                	if (error.contains("1")) {
                		req.setAttribute("errorMessage", "Thiếu thông tin giao hàng");
                	} else if(error.contains("2")){
                		req.setAttribute("errorMessage", "Chưa chọn phương thức thanh toán");
                	}
                	else {
                		req.setAttribute("errorMessage", "LỖI HỆ THỐNG");
                	}
                	
                }

                // Đặt thuộc tính order vào request và chuyển tiếp tới JSP
                req.setAttribute("order", order);
                req.setAttribute("ship", 30000);
                req.setAttribute("selectedVoucher", selectedVoucher);
                req.setAttribute("listVoucher", listVoucherCanApply);
                req.setAttribute("listVoucherCanNotApply", listVoucherCanNotApply);
                String paymentCode = "https://img.vietqr.io/image/VCB-1025984614-compact.png?amount=" + (int)order.getActualCost() + "&addInfo=<" + "Thanh toán đơn hàng " + order.getOrderId() + ">&accountName=PHAMQUYNHTHU";
                req.setAttribute("paymentCode", paymentCode);
                req.getRequestDispatcher("/customer/views/Order.jsp").forward(req, resp);

            } catch (Exception e) {
                // Log lỗi chi tiết
                e.printStackTrace();  // Hoặc sử dụng một logger để ghi lại lỗi
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
            }
    	} else if (url.contains("/customer/confirmOrder")) {
    		Order order = orderService.findById(Integer.parseInt(req.getParameter("orderId")));
    		String status = order.getStatus();
			if (status.contains("thanh toán"))
				req.getRequestDispatcher("/customer/views/confirmOrder.jsp").forward(req, resp);
			else {
				resp.sendRedirect(req.getContextPath() + "/customer/order?error=" + "3");
			}
    	}
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
    	if (url.contains("voucher")) {
    		String selectedVoucherCode = req.getParameter("selectedVoucher");
			if (selectedVoucherCode != null && selectedVoucherCode != "") {
				try {
					Order order = orderService.findById(Integer.parseInt(req.getParameter("orderId")));
					boolean isVoucherSelected = false;
					int voucherCodeInt = Integer.parseInt(selectedVoucherCode);
					
					//List voucher có thể áp cho order
					List<VoucherDto> listVoucherCanApply = voucherService.voucherByPriceForOder(order);
					List<VoucherDto> listVoucherByProduct = voucherService.voucherByProductForOder(order);
					listVoucherCanApply.addAll(listVoucherByProduct);
					
			        // Check if the voucherCodeInt exists in the list
			        isVoucherSelected = listVoucherCanApply.stream()
			                .anyMatch(voucher -> voucher.getVoucherId() == voucherCodeInt);
					
					if (isVoucherSelected == true) {
						resp.sendRedirect(req.getContextPath() + "/customer/order?selectedVoucher=" + voucherCodeInt);
					} else {
						resp.sendRedirect(req.getContextPath() + "/customer/order");
					}
			        			
				}catch (Exception e) {
		            e.printStackTrace();  
		        }
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/customer/order");
			}
		}
    	else if (url.contains("shippingInfo")) {
    		// Lấy giá trị từ form
            String phone = req.getParameter("phone");
            String cityOfProvince = req.getParameter("cityOfProvince");
            String district = req.getParameter("district");
            String ward = req.getParameter("ward");
            String streetNumber = req.getParameter("streetNumber");
            // Nếu chọn "Khác", lấy giá trị từ trường input khác
            if ("other".equals(cityOfProvince)) {
                cityOfProvince = req.getParameter("cityOther");
            }
            if ("other".equals(district)) {
                district = req.getParameter("districtOther");
            }
            if ("other".equals(ward)) {
                ward = req.getParameter("wardOther");
            }
            String errorMessage = "";
            
            if (phone == null || phone.isEmpty() || !phone.matches("\\d{10,11}")) {
                errorMessage = "Số điện thoại không hợp lệ. Vui lòng nhập lại!";
            } else if (cityOfProvince == null || cityOfProvince.isEmpty()) {
                errorMessage = "Vui lòng nhập Tỉnh/Thành phố!";
            } else if (district == null || district.isEmpty()) {
                errorMessage = "Vui lòng nhập Quận/Huyện!";
            } else if (ward == null || ward.isEmpty()) {
                errorMessage = "Vui lòng nhập Phường/Xã!";
            } else if (streetNumber == null || streetNumber.isEmpty()) {
                errorMessage = "Vui lòng nhập Số nhà/Đường!";
            }

            if (errorMessage.isEmpty()) {
                
                Order order = orderService.findById(Integer.parseInt(req.getParameter("orderId")));
                order.setPhone(phone);
                order.setCityOfProvince(cityOfProvince);
                order.setDistrict(district);
                order.setWard(ward);
                order.setStreetNumber(streetNumber);
                
                orderService.update(order);
            }
            resp.sendRedirect(req.getContextPath() + "/customer/order");
            
    	}
    	else if (url.contains("paymentMethod")) {
    		// Lấy thông tin phương thức thanh toán từ form
            String paymentMethod = req.getParameter("paymentMethod");
            Order order = orderService.findById(Integer.parseInt(req.getParameter("orderId")));

            if (order == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không tìm thấy thông tin đơn hàng.");
                return;
            }
            if (order.getPhone() == null || order.getPhone().isEmpty() || order.getCityOfProvince() == null ||order.getCityOfProvince().isEmpty() 
            		|| order.getDistrict() == null || order.getDistrict().isEmpty() 
            		|| order.getWard() == null || order.getWard().isEmpty() 
            		|| order.getStreetNumber() == null || order.getStreetNumber().isEmpty()) {
            	resp.sendRedirect(req.getContextPath() + "/customer/order?error=" + "1");
                return;
            }
            if ("cod".equals(paymentMethod)) {
                // Thanh toán khi nhận hàng
                order.setStatus("Chưa thanh toán");
                orderService.update(order);
                resp.sendRedirect(req.getContextPath() + "/customer/confirmOrder?orderId=" + order.getOrderId());
            } else if ("online".equals(paymentMethod)) {
      
                order.setStatus("Chưa duyệt thanh toán");
                orderService.update(order);
                resp.sendRedirect(req.getContextPath() + "/customer/confirmOrder?orderId=" + order.getOrderId());
            } else {
                // Phương thức thanh toán không hợp lệ
            	resp.sendRedirect(req.getContextPath() + "/customer/order?error=" + "2");
            }
    	}
	}
    
}
