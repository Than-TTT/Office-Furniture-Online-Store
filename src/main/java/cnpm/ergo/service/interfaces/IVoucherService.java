package cnpm.ergo.service.interfaces;

import java.util.List;

import cnpm.ergo.entity.Order;
import cnpm.ergo.entity.OrderItem;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherDto;

public interface IVoucherService {
	public Voucher findById(int id);
	public List<VoucherDto> voucherByPriceForOder(Order order);
	public List<VoucherDto> voucherByProductForOder(Order order);
	public List<VoucherDto> voucherByPriceNotForOder(Order order);
	public double CountDiscountPrice(List<OrderItem> orderItems, Voucher voucher);
}
