package dao;

import java.util.List;

public interface IVoucherByPriceDAO {
    void insert(model.VoucherByPrice voucher);
    void update(model.VoucherByPrice voucher);
    void delete(model.VoucherByPrice voucher);
    List<model.VoucherByPrice> findAll();
    model.VoucherByPrice findById (int Id);
    public List<model.VoucherByPrice> findAll(int pageNo, int pageSize);
}
