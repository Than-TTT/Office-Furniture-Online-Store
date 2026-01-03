package dao;

import model.Voucher;
import model.VoucherByPrice;

import java.util.List;

public interface IVoucherByPriceDAO {
    void insert(VoucherByPrice voucher);
    void update(VoucherByPrice voucher);
    void delete(VoucherByPrice voucher);
    List<VoucherByPrice> findAll();
    VoucherByPrice findById (int Id);
    public List<VoucherByPrice> findAll(int pageNo, int pageSize);
}
