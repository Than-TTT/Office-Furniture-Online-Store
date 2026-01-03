package dao;
import java.util.List;

import model.ProductType;

public interface IProductType {
	void insert(ProductType productType);
	void update(ProductType productType);
	void delete(int typeId);
	ProductType findById(int typeId);
	List<ProductType> findAll();
	int count();

	List<ProductType> findAllByPage(int offset, int limit);

}

