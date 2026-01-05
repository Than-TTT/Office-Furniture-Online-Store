package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.IVoucherByProductDAOImpl;
import cnpm.ergo.DAO.interfaces.IVoucherByProductDAO;
import cnpm.ergo.entity.Voucher;
import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.interfaces.IVoucherByProductService;

import java.util.List;

public class IVoucherByProductServiceImpl implements IVoucherByProductService {
    IVoucherByProductDAO voucherDao = new IVoucherByProductDAOImpl();
    @Override
    public void insert(VoucherByProduct voucher) {
        voucherDao.insert(voucher);
    }

    @Override
    public void update(VoucherByProduct voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public void delete(VoucherByProduct voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public List<VoucherByProduct> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public VoucherByProduct findById(int Id) {
        return voucherDao.findById(Id);
    }

    @Override
    public List<VoucherByProduct> findAll(int pageNo, int pageSize) {
        return voucherDao.findAll(pageNo,pageSize);
    }
}
