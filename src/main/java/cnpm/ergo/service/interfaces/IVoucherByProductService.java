package cnpm.ergo.service.interfaces;

import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;

import java.util.List;

public interface IVoucherByProductService {
    void insert(VoucherByProduct voucher);
    void update(VoucherByProduct voucher);
    void delete(VoucherByProduct voucher);
    List<VoucherByProduct> findAll();
    VoucherByProduct findById (int Id);
    public List<VoucherByProduct> findAll(int pageNo, int pageSize);
}
