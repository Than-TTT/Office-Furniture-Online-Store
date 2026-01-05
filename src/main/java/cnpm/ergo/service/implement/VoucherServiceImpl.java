package cnpm.ergo.service.implement;

import java.util.List;

import cnpm.ergo.DAO.implement.*;
import cnpm.ergo.DAO.interfaces.IVoucherDao;
import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.OrderItem;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherDto;
import cnpm.ergo.service.interfaces.IOrderService;
import cnpm.ergo.service.interfaces.IVoucherService;
import cnpm.ergo.service.implement.OrderServiceImpl;

public class VoucherServiceImpl implements IVoucherService{
	
	private final IVoucherDao voucherSer = new VoucherDaoImpl();
	
	
	@Override
	public Voucher findById(int id) {
		return voucherSer.findById(id);
	}

	@Override
	public List<VoucherDto> voucherByPriceForOder(Order order) {
		IOrderService orderService = new OrderServiceImpl();
		if (orderService.findById(order.getOrderId()) == null)
			return null;
		else return voucherSer.voucherByPriceForOder(order);
	}
	
	

	@Override
	public List<VoucherDto> voucherByProductForOder(Order order) {
		return voucherSer.voucherByProductForOder(order);
	}

	@Override
	public List<VoucherDto> voucherByPriceNotForOder(Order order) {
		IOrderService orderService = new OrderServiceImpl();
		if (orderService.findById(order.getOrderId()) == null)
			return null;
		else return voucherSer.voucherByPriceNotForOder(order);
	}

	@Override
	public double CountDiscountPrice(List<OrderItem> orderItems, Voucher voucher) {
		return voucherSer.CountDiscountPrice(orderItems, voucher);
	}
	
	
	
}
