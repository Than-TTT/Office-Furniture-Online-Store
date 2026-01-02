package dao;

import model.OrderItem;
import java.util.List;

public interface IOrderItemDao {
    
    /**
     * Lấy tất cả items của một đơn hàng
     */
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    
    /**
     * Lấy chi tiết một order item
     */
    OrderItem getOrderItemById(int orderItemId);
    
    /**
     * Thêm item vào đơn hàng
     */
    void addOrderItem(OrderItem orderItem);
}