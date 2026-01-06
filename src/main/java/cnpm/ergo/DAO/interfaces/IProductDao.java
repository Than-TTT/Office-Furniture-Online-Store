package cnpm.ergo.DAO.interfaces;

import java.util.List;

import org.hibernate.query.Page;

import cnpm.ergo.entity.Category;
import cnpm.ergo.entity.Product;
public interface IProductDao {
	void insert(Product product);
	void update(Product product);
	void delete(int productId);
	Product findById(int productId);
	int count(String categoryId);

	List<Product> findAll();

	List<Product> searchByName(String name);
	int count();
  List<Product> findAllList(int page, int size);
	List<String> findAllColors();
	List<String> findAllMaterials();
	List<Double> findAllHeights();
	List<Double> findAllLengths();
	long Count(String keyword, String categoryName, String filterPrice, String[] colors, String[] materials,
			String[] heights, String[] lengths);
	List<Product> findRelatedProductsByProductId(int productId, int page, int pageSize);
	long getTotalRelatedProducts(int productId);
	List<Product> findByKeywordOrCategory(String keyword, String categoryName);
	List<Product> applyFiltersAfterKeywordOrCategory(List<Long> productIdsLong, String filterPrice, String[] colors,
			String[] materials, String[] heights, String[] lengths);
	List<Product> findProductsByPage(int offset, int limit);
	List<Product> findProductsAvailableByPage(int offset, int limit);
	int countAvailable();

}

