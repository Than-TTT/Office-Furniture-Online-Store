package dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VoucherDaoImpl implements IVoucherDao{
	
	
	@Override
	public Voucher findById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
        return em.find(Voucher.class, id);
	}



	@Override
	public List<VoucherDto> voucherByPriceForOder(Order order) {
	    EntityManager em = JPAConfig.getEntityManager();

	    String jpql = "SELECT new cnpm.ergo.entity.VoucherDto(vp.lowerbound, vp.voucherId, v.voucherId, v.dateStart, v.dateEnd, v.code, v.discount) " +
	                  "FROM VoucherByPrice vp " +
	                  "JOIN Voucher v ON vp.voucherId = v.voucherId " +  
	                  "WHERE vp.lowerbound <= :totalCost " + 
	                  "AND :orderDate BETWEEN v.dateStart AND v.dateEnd";  

	    TypedQuery<VoucherDto> query = em.createQuery(jpql, VoucherDto.class);
	    query.setParameter("totalCost", order.getTotalCost());
	    query.setParameter("orderDate", order.getOrderDate());  // Truyền orderDate từ Order vào câu truy vấn

	    return query.getResultList();
	}

	

	@Override
	public List<VoucherDto> voucherByProductForOder(Order order) {
		EntityManager em = JPAConfig.getEntityManager();
		// Truy vấn để lấy các VoucherByProduct phù hợp
	    String queryStr = """
	        SELECT v FROM VoucherByProduct v
	        WHERE :orderDate BETWEEN v.dateStart AND v.dateEnd
	    """;

	    TypedQuery<VoucherByProduct> query = em.createQuery(queryStr, VoucherByProduct.class);
	    query.setParameter("orderDate", order.getOrderDate());

	    List<VoucherByProduct> vouchers = query.getResultList();
	    List<VoucherDto> voucherDtos = new ArrayList<>();

	    // Lọc theo điều kiện typeId
	    for (VoucherByProduct voucher : vouchers) {
	        for (OrderItem orderItem : order.getOrderItems()) {
	            if (voucher.getProductTypes().stream()
	                    .anyMatch(productType -> productType.getTypeId() == orderItem.getProductType().getTypeId())) {

	                // Tạo đối tượng VoucherDto và thêm vào danh sách
	                VoucherDto dto = new VoucherDto();
	                dto.setLowerbound(0); 
	                dto.setVoucherByPriceId(0); // 0 là voucher By Product
	                dto.setVoucherId(voucher.getVoucherId());
	                dto.setDateStart(voucher.getDateStart());
	                dto.setDateEnd(voucher.getDateEnd());
	                dto.setCode(voucher.getCode());
	                dto.setDiscount(voucher.getDiscount());

	                voucherDtos.add(dto);
	            }
	        }
	    }

	    return voucherDtos;
	}


	@Override
    public List<VoucherDto> voucherByPriceNotForOder(Order order) {
		EntityManager em = JPAConfig.getEntityManager();

		String jpql = "SELECT new cnpm.ergo.entity.VoucherDto(vp.lowerbound, vp.voucherId, v.voucherId, v.dateStart, v.dateEnd, v.code, v.discount) " +
	                "FROM VoucherByPrice vp " +
	                "JOIN Voucher v ON vp.voucherId = v.voucherId " +  
	                "WHERE vp.lowerbound > :totalCost " + 
	                "AND :orderDate BETWEEN v.dateStart AND v.dateEnd";  
	
	  TypedQuery<VoucherDto> query = em.createQuery(jpql, VoucherDto.class);
	  query.setParameter("totalCost", order.getTotalCost());
	  query.setParameter("orderDate", order.getOrderDate());  
	
	  return query.getResultList();
    }
	
	@Override
	public double CountDiscountPrice(List<OrderItem> orderItems, Voucher voucher) {
	    // Kiểm tra nếu voucher là đối tượng VoucherByProduct
	    if (voucher instanceof VoucherByProduct) {
	        VoucherByProduct voucherByProduct = (VoucherByProduct) voucher;

	        double discountAmount = 0.0;

	        // Duyệt qua từng OrderItem trong danh sách
	        for (OrderItem orderItem : orderItems) {
	            // Kiểm tra nếu typeId của OrderItem có trong danh sách typeId của VoucherByProduct
	            for (ProductType productType : voucherByProduct.getProductTypes()) {
	            	if (productType.getTypeId() == orderItem.getProductType().getTypeId()) {
	                    // Nếu typeId khớp, tính số tiền giảm
	                    discountAmount += orderItem.getPrice()
								.multiply(BigDecimal.valueOf(orderItem.getQuantity()))
								.multiply(BigDecimal.valueOf(voucher.getDiscount()))
								.doubleValue();
	                }
	            }
	        }

	        return discountAmount;
	    }

	    // Trả về 0 nếu voucher không phải là VoucherByProduct hoặc không thỏa điều kiện
	    return 0.0;
	}


	
	public static void main(String[] args) {
//		VoucherDaoImpl v = new VoucherDaoImpl();
//		OrderDaoImpl od = new OrderDaoImpl();
//		Order o = od.findById(1);
//		List<VoucherDto> listVoucherCanApply = v.voucherByPriceForOder(o);
//		List<VoucherDto> listVoucherByProduct = v.voucherByProductForOder(o);
//		listVoucherCanApply.addAll(listVoucherByProduct);
//		System.out.print(listVoucherCanApply);
	}
	
}
