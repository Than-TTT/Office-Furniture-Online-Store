package service;

import model.Voucher;
import model.VoucherByPrice;
import model.VoucherByProduct;

import java.util.List;

public interface IVoucherByProductService {
    void insert(VoucherByProduct voucher);
    void update(VoucherByProduct voucher);
    void delete(VoucherByProduct voucher);
    List<VoucherByProduct> findAll();
    VoucherByProduct findById (int Id);
    public List<VoucherByProduct> findAll(int pageNo, int pageSize);
}
