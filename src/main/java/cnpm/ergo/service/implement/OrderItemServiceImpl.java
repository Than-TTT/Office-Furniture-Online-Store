package cnpm.ergo.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import cnpm.ergo.DAO.implement.OrderItemDaoImpl;
import cnpm.ergo.DAO.implement.ProductDaoImpl;
import cnpm.ergo.DAO.interfaces.IOrderItemDao;
import cnpm.ergo.DAO.interfaces.IProductDao;
import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.OrderItem;
import cnpm.ergo.service.interfaces.IOrderItemService;

public class OrderItemServiceImpl implements IOrderItemService{
	private final IOrderItemDao orderItemDao = new OrderItemDaoImpl();
	
	@Override
	public void insert(Order order, OrderItem orderItem) {
		orderItemDao.insert(order, orderItem);
		
	}

	@Override
	public void update(Order order, OrderItem orderItem) {
		orderItemDao.update(order, orderItem);
		
	}

	@Override
	public void delete(Order order, int orderItemId) {
		orderItemDao.delete(order, orderItemId);
		
	}

	@Override
	public List<OrderItem> findAll(int orderId) {
		return orderItemDao.findAll(orderId);
	}

	@Override
	public int count(int orderId) {
		return orderItemDao.count(orderId);
	}
	
	@Override
    public List<OrderItem> findByProductName(String productName) {
        return orderItemDao.findByProductNameForOrderItem(productName);
    }
}
