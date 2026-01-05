package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.IVoucherByPriceDAOImpl;
import cnpm.ergo.DAO.implement.IVoucherByProductDAOImpl;
import cnpm.ergo.DAO.interfaces.IVoucherByPriceDAO;
import cnpm.ergo.DAO.interfaces.IVoucherByProductDAO;
import cnpm.ergo.entity.VoucherByPrice;
import cnpm.ergo.entity.VoucherByProduct;
import cnpm.ergo.service.interfaces.IVoucherByPriceService;

import java.util.List;

public class IVoucherByPriceServiceImpl implements IVoucherByPriceService {
    IVoucherByPriceDAO voucherDao = new IVoucherByPriceDAOImpl();
    @Override
    public void insert(VoucherByPrice voucher) {
        voucherDao.insert(voucher);
    }

    @Override
    public void update(VoucherByPrice voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public void delete(VoucherByPrice voucher) {
        voucherDao.delete(voucher);
    }

    @Override
    public List<VoucherByPrice> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public VoucherByPrice findById(int Id) {
        return voucherDao.findById(Id);
    }

    @Override
    public List<VoucherByPrice> findAll(int pageNo, int pageSize) {
        return voucherDao.findAll(pageNo,pageSize);
    }
}
