package dao;

import model.Order;
import java.util.List;

public interface IOrderDao {
    
    /**
     * Lấy tất cả đơn hàng của khách hàng
     */
    List<Order> getOrdersByCustomerId(int customerId);
    
    /**
     * Lấy chi tiết đơn hàng theo ID
     */
    Order getOrderById(int orderId);
    
    /**
     * Lấy đơn hàng của khách hàng theo trạng thái
     */
    List<Order> getOrdersByCustomerIdAndStatus(int customerId, String status);
    
    /**
     * Tạo đơn hàng mới
     */
    void createOrder(Order order);
    
    /**
     * Cập nhật trạng thái đơn hàng
     */
    void updateOrderStatus(int orderId, String status);
}