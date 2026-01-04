package dao;

import java.util.List;

public interface IVoucherDao {
	model.Voucher findById(int id);
	List<model.VoucherDto> voucherByPriceForOder(model.Order order);
	List<model.VoucherDto> voucherByProductForOder(model.Order order);
	List<model.VoucherDto> voucherByPriceNotForOder(model.Order order);
	public double CountDiscountPrice(List<model.OrderItem> orderItems, model.Voucher voucher);
}
