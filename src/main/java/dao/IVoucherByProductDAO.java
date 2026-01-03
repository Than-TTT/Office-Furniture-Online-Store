package dao;


import java.util.List;

public interface IVoucherByProductDAO {
    void insert(model.VoucherByProduct voucher);
    void update(model.VoucherByProduct voucher);
    void delete(model.VoucherByProduct voucher);
    List<model.VoucherByProduct> findAll();
    model.VoucherByProduct findById (int Id);
    List<model.VoucherByProduct> findAll(int pageNo, int pageSize);
}
