package service;

import java.util.List;

public interface IVoucherByProductService {
    void insert(model.VoucherByProduct voucher);
    void update(model.VoucherByProduct voucher);
    void delete(model.VoucherByProduct voucher);
    List<model.VoucherByProduct> findAll();
    model.VoucherByProduct findById (int Id);
    public List<model.VoucherByProduct> findAll(int pageNo, int pageSize);
}
