package dao;

import java.util.List;

import model.ProductImage;

public interface IProductImage {
	void insert(ProductImage productImage);
	void delete(ProductImage productImage);
	List<ProductImage> findByProductId(int productId);
	int count();
	List<ProductImage> findAll();
}
