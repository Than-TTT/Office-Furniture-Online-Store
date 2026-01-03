package dao;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.Voucher;
import model.VoucherDto;

public interface IVoucherDao {
	Voucher findById(int id);
	List<VoucherDto> voucherByPriceForOder(Order order);
	List<VoucherDto> voucherByProductForOder(Order order);
	List<VoucherDto> voucherByPriceNotForOder(Order order);
	public double CountDiscountPrice(List<OrderItem> orderItems, Voucher voucher);
}
