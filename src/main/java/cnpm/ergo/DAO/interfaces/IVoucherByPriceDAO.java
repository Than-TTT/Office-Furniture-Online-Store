package cnpm.ergo.DAO.interfaces;

import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;

import java.util.List;

public interface IVoucherByPriceDAO {
    void insert(VoucherByPrice voucher);
    void update(VoucherByPrice voucher);
    void delete(VoucherByPrice voucher);
    List<VoucherByPrice> findAll();
    VoucherByPrice findById (int Id);
    public List<VoucherByPrice> findAll(int pageNo, int pageSize);
}
