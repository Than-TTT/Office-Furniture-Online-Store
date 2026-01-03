package dao;

import model.ProductType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

import org.hibernate.Hibernate;

import dao.IProductDao;
import config.JPAConfig;
import model.Product;

public class ProductDaoImpl implements IProductDao {

    @Override
    public void insert(Product product) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(product); // Thêm mới sản phẩm
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

	@Override
	public List<Product> findAllList(int page, int size) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT p FROM Product p "
					+ "LEFT JOIN p.productImages "
					+ "LEFT JOIN p.productTypes pt "
					+ "LEFT JOIN p.category "
					+ "WHERE p.isDelete = false";
			List<Product> products = em.createQuery(jpql, Product.class)
					.setFirstResult((page - 1) * size)
					.setMaxResults(size)
					.getResultList();

			return products;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
    @Override
    public void update(Product product) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(product); // Cập nhật sản phẩm
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int productId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                product.setDelete(true); // Xóa sản phẩm
                em.merge(product);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Product findById(int productId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            Product product = em.find(Product.class, productId); // Tìm sản phẩm theo ID
            trans.commit();
            return product;
        }
        catch (Exception ex) {
            trans.rollback();
            throw ex;
        }
    }

    @Override
	public List<Product> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        String jpql = "SELECT p FROM Product p";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        trans.commit();
        em.close();
        return query.getResultList(); // Lấy danh sách tất cả sản phẩm
    }
    public List<Product> findAllAvailable() {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        String jpql = "SELECT p FROM Product p WHERE p.isDelete == false";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        trans.commit();
        em.close();
        return query.getResultList(); // Lấy danh sách tất cả sản phẩm
    }

    @Override
	public List<Product> searchByName(String name) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        String jpql = "SELECT p FROM Product p WHERE p.name LIKE :name";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("name", "%" + name + "%");
        trans.commit();
        em.close();
        return query.getResultList(); // Tìm sản phẩm theo tên
    }

	
	@Override
	public int count(String categoryId) {
		EntityManager em = JPAConfig.getEntityManager();
		String jpql = "SELECT COUNT(p) FROM Product p WHERE p.isDelete = false";

		if (categoryId != null && !categoryId.isEmpty()) {
			jpql += " AND p.category.categoryId = :categoryId";
		}

		TypedQuery<Long> query = em.createQuery(jpql, Long.class);

		if (categoryId != null && !categoryId.isEmpty()) {
			query.setParameter("categoryId", Integer.parseInt(categoryId));
		}

		return query.getSingleResult().intValue();
	}

	@Override
	public List<String> findAllColors() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT DISTINCT pt.color FROM ProductType pt WHERE pt.color IS NOT NULL";
			return em.createQuery(jpql, String.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Double> findAllLengths() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT DISTINCT pt.length FROM ProductType pt WHERE pt.length IS NOT NULL";
			return em.createQuery(jpql, Double.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Double> findAllHeights() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT DISTINCT pt.height FROM ProductType pt WHERE pt.height IS NOT NULL";
			return em.createQuery(jpql, Double.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<String> findAllMaterials() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT DISTINCT pt.material FROM ProductType pt WHERE pt.material IS NOT NULL";
			return em.createQuery(jpql, String.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Product> findByKeywordOrCategory(String keyword, String categoryName) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder("SELECT DISTINCT p FROM Product p ");
	        jpql.append("LEFT JOIN FETCH p.productTypes pt ");  // Chỉ tải productTypes trong truy vấn này
	        jpql.append("LEFT JOIN p.category c ");
	        jpql.append("WHERE p.isDelete = false ");

	        if (categoryName != null && !categoryName.isEmpty()) {
	            jpql.append("AND c.categoryName LIKE :categoryName ");
	        }
	        if (keyword != null && !keyword.isEmpty()) {
	            jpql.append("AND (p.name LIKE :keyword OR p.descript LIKE :keyword) ");
	        }

	        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);
	        if (categoryName != null && !categoryName.isEmpty()) {
	            query.setParameter("categoryName", "%" + categoryName + "%");
	        }
	        if (keyword != null && !keyword.isEmpty()) {
	            query.setParameter("keyword", "%" + keyword + "%");
	        }
	        List<Product> products = query.getResultList();

	        for (Product product : products) {
	            Hibernate.initialize(product.getProductImages());
	        }

	        return products;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
	@Override
	public List<Product> applyFiltersAfterKeywordOrCategory(List<Long> productIdsLong, String filterPrice,
	                                                          String[] colors, String[] materials, String[] heights, String[] lengths) {
	    if (productIdsLong == null || productIdsLong.isEmpty()) {
	        return List.of();
	    }

	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder("SELECT DISTINCT p FROM Product p ");

	        jpql.append("LEFT JOIN FETCH p.productTypes pt ");
	        jpql.append("LEFT JOIN p.category c ");
	        jpql.append("WHERE p.productId IN :productIds ");

	        // Apply filter conditions
	        if (filterPrice != null && !filterPrice.isEmpty()) {
	            jpql.append("AND pt.price BETWEEN :minPrice AND :maxPrice ");
	        }
	        if (colors != null && colors.length > 0) {
	            jpql.append("AND pt.color IN :colors ");
	        }
	        if (materials != null && materials.length > 0) {
	            jpql.append("AND pt.material IN :materials ");
	        }
	        if (heights != null && heights.length > 0) {
	            jpql.append("AND pt.height IN :heights ");
	        }
	        if (lengths != null && lengths.length > 0) {
	            jpql.append("AND pt.length IN :lengths ");
	        }

	        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);
	        query.setParameter("productIds", productIdsLong);

	        if (filterPrice != null && !filterPrice.isEmpty()) {
	            String[] prices = filterPrice.split("-");
	            query.setParameter("minPrice", Double.parseDouble(prices[0]));
	            query.setParameter("maxPrice", Double.parseDouble(prices[1]));
	        }

	        if (colors != null && colors.length > 0) {
	            query.setParameter("colors", List.of(colors));
	        }
	        if (materials != null && materials.length > 0) {
	            query.setParameter("materials", List.of(materials));
	        }
	        if (heights != null && heights.length > 0) {
	            query.setParameter("heights", List.of(heights));
	        }
	        if (lengths != null && lengths.length > 0) {
	            query.setParameter("lengths", List.of(lengths));
	        }


	        List<Product> products = query.getResultList();

	        for (Product product : products) {
	            Hibernate.initialize(product.getProductImages());
	        }

	        return products;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
	@Override
	public long Count(String keyword, String categoryName, String filterPrice,
	                            String[] colors, String[] materials, String[] heights, String[] lengths) {
	    EntityManager em = JPAConfig.getEntityManager();
	    try {
	        StringBuilder jpql = new StringBuilder("SELECT COUNT(DISTINCT p) FROM Product p ");
	        jpql.append("LEFT JOIN p.productTypes pt ");
	        jpql.append("LEFT JOIN p.category c ");
	        jpql.append("WHERE p.isDelete = false ");
	        if (categoryName != null && !categoryName.isEmpty()) {
	            jpql.append("AND c.categoryName LIKE :categoryName ");
	        }
	        if (keyword != null && !keyword.isEmpty()) {
	            jpql.append("AND (p.name LIKE :keyword OR p.descript LIKE :keyword) ");
	        }
	        if (filterPrice != null && !filterPrice.isEmpty()) {
	            jpql.append("AND pt.price BETWEEN :minPrice AND :maxPrice ");
	        }

	        if (colors != null && colors.length > 0) {
	            jpql.append("AND pt.color IN :colors ");
	        }
	        if (materials != null && materials.length > 0) {
	            jpql.append("AND pt.material IN :materials ");
	        }
	        if (heights != null && heights.length > 0) {
	            jpql.append("AND pt.height IN :heights ");
	        }
	        if (lengths != null && lengths.length > 0) {
	            jpql.append("AND pt.length IN :lengths ");
	        }

	        TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
	        if (categoryName != null && !categoryName.isEmpty()) {
	            query.setParameter("categoryName", "%" + categoryName + "%");
	        }
	        if (keyword != null && !keyword.isEmpty()) {
	            query.setParameter("keyword", "%" + keyword + "%");
	        }
	        if (filterPrice != null && !filterPrice.isEmpty()) {
	            try {
	                String[] prices = filterPrice.split("-");
	                query.setParameter("minPrice", Double.parseDouble(prices[0]));
	                query.setParameter("maxPrice", Double.parseDouble(prices[1]));
	            } catch (NumberFormatException e) {
	                throw new IllegalArgumentException("Invalid price range format: " + filterPrice, e);
	            }
	        }
	        if (colors != null && colors.length > 0) {
	            query.setParameter("colors", List.of(colors));
	        }
	        if (materials != null && materials.length > 0) {
	            query.setParameter("materials", List.of(materials));
	        }
	        if (heights != null && heights.length > 0) {
	            query.setParameter("heights", List.of(heights));
	        }
	        if (lengths != null && lengths.length > 0) {
	            query.setParameter("lengths", List.of(lengths));
	        }
	        return query.getSingleResult();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
	@Override
    public List<Product> findRelatedProductsByProductId(int productId, int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Product currentProduct = em.find(Product.class, productId);
            if (currentProduct == null) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại.");
            }

            String categoryName = currentProduct.getCategory().getCategoryName();

            StringBuilder jpql = new StringBuilder("SELECT DISTINCT p FROM Product p ");
            jpql.append("LEFT JOIN FETCH p.productTypes pt ");
            jpql.append("LEFT JOIN p.category c ");
            jpql.append("WHERE p.isDelete = false ");
            jpql.append("AND c.categoryName = :categoryName ");
            jpql.append("AND p.productId != :productId ");
            jpql.append("ORDER BY p.productId");

            TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);
            query.setParameter("categoryName", categoryName);
            query.setParameter("productId", productId);

            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);

            List<Product> relatedProducts = query.getResultList();

            for (Product product : relatedProducts) {
                Hibernate.initialize(product.getProductTypes());
                Hibernate.initialize(product.getProductImages());
            }

            return relatedProducts;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public int count() {
            EntityManager em = JPAConfig.getEntityManager();
            EntityTransaction trans = em.getTransaction();

            try {
                trans.begin();
                String jpql = "SELECT COUNT(p) FROM Product p";
                Query query = em.createQuery(jpql);
                Long result = (Long) query.getSingleResult();
                trans.commit();
                return result.intValue(); // Đếm tổng số sản phẩm
            } catch (Exception e) {
                trans.rollback();
                throw e;
            } finally {
                em.close();
            }
    }

    @Override
    public List<Product> findProductsByPage(int offset, int limit) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class)
                .setFirstResult(offset)
                .setMaxResults(limit);
        List<Product> products = query.getResultList();
        trans.commit();
        return products;
    }
    public List<Product> findProductsAvailableByPage(int offset, int limit) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            String jpql = "SELECT p FROM Product p WHERE p.isDelete = false";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class)
                    .setFirstResult(offset).setMaxResults(limit);
            trans.commit();
            return query.getResultList(); // Lấy danh sách tất cả sản phẩm
        }
        catch (Exception e) {
            trans.rollback();
            throw e;
        }
        finally {
            em.close();
        }
    }

    @Override
    public int countAvailable() {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            String jpql = "SELECT COUNT(p) FROM Product p where p.isDelete = false";
            Query query = em.createQuery(jpql);
            Long result = (Long) query.getSingleResult();
            trans.commit();
            return result.intValue();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public long getTotalRelatedProducts(int productId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Product currentProduct = em.find(Product.class, productId);
            if (currentProduct == null) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại.");
            }

            String categoryName = currentProduct.getCategory().getCategoryName();
            StringBuilder jpql = new StringBuilder("SELECT COUNT(DISTINCT p) FROM Product p ");
            jpql.append("LEFT JOIN p.category c ");
            jpql.append("WHERE p.isDelete = false ");
            jpql.append("AND c.categoryName = :categoryName ");
            jpql.append("AND p.productId != :productId");

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            query.setParameter("categoryName", categoryName);
            query.setParameter("productId", productId);

            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
            List<Long> productIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);
            String filterPrice = null;  // Price range
            String[] colors = null;
            String[] materials =null;
            String[] heights =null;
            String[] lengths = null;

            int page = 2;
            int size = 3;

            List<Product> products = new ProductDaoImpl().applyFiltersAfterKeywordOrCategory(
                    productIds, filterPrice, colors, materials, heights, lengths);

            // Print the products to verify the output
            if (products != null && !products.isEmpty()) {
                for (Product product : products) {
                    System.out.println("Product ID: " + product.getProductId());
                    for (ProductType pt : product.getProductTypes()) {
                        System.out.println("Color: " + pt.getColor() + ", Price: " + pt.getPrice());
                    }
                }
            } else {
                System.out.println("No products found.");
            }
    }

}
