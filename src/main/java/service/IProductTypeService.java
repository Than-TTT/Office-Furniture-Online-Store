package service;

import java.util.List;

import model.ProductType;

public interface IProductTypeService {
	void addProductType(ProductType productType);
	void updateProductType(ProductType productType);
	void deleteProductType(int typeId);
	ProductType getProductTypeById(int typeId);
	List<ProductType> getAllProductTypes();
	int getProductTypeCount();

	List<ProductType> getAllProductTypesByPage(int offset, int limit);

}
