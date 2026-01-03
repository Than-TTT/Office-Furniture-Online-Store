package service;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.Voucher;
import model.VoucherDto;

public interface IVoucherService {
	public Voucher findById(int id);
	public List<VoucherDto> voucherByPriceForOder(Order order);
	public List<VoucherDto> voucherByProductForOder(Order order);
	public List<VoucherDto> voucherByPriceNotForOder(Order order);
	public double CountDiscountPrice(List<OrderItem> orderItems, Voucher voucher);
}
