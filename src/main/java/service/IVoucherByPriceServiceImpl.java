package service;

import java.util.List;

public class IVoucherByPriceServiceImpl implements service.IVoucherByPriceService {
    dao.IVoucherByPriceDAO voucherDao = new dao.IVoucherByPriceDAOImpl();
    @Override
    public void insert(model.VoucherByPrice voucher) {
        voucherDao.insert(voucher);
    }

    @Override
    public void update(model.VoucherByPrice voucher) {
        voucherDao.update(voucher);
    }

    @Override
    public void delete(model.VoucherByPrice voucher) {
        voucherDao.delete(voucher);
    }

    @Override
    public List<model.VoucherByPrice> findAll() {
        return voucherDao.findAll();
    }

    @Override
    public model.VoucherByPrice findById(int Id) {
        return voucherDao.findById(Id);
    }

    @Override
    public List<model.VoucherByPrice> findAll(int pageNo, int pageSize) {
        return voucherDao.findAll(pageNo,pageSize);
    }
}
