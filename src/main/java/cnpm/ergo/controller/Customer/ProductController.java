package cnpm.ergo.controller.Customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.Category;
import cnpm.ergo.entity.Product;
import cnpm.ergo.entity.Review;
import cnpm.ergo.entity.User;
import cnpm.ergo.service.interfaces.IProductService;
import cnpm.ergo.service.implement.ProductServiceImpl;
import cnpm.ergo.service.interfaces.ICategoryService;
import cnpm.ergo.service.implement.CategoryServiceImpl;
import cnpm.ergo.service.implement.ReviewServiceImpl;
import cnpm.ergo.service.interfaces.IReviewService;
import cnpm.ergo.service.implement.CartServiceImpl;
@WebServlet(urlPatterns = { "/products/detail", "/products/search", "/customer/products/detail", "/customer/products/search", "/customer/cart/add" })
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IProductService productService = new ProductServiceImpl();
	private final ICategoryService categoryService = new CategoryServiceImpl();
	private final IReviewService reviewService = new ReviewServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			loadCommonAttributes(req);
			String action = req.getServletPath();

			// Phân biệt giữa khách hàng đăng nhập và người dùng không đăng nhập
			boolean isCustomer = isCustomerSession(req);

			switch (action) {
				case "/products/detail":
				case "/customer/products/detail":
					getProductDetail(req, resp);
					break;
				case "/products/search":
				case "/customer/products/search":
					searchProducts(req, resp);
					break;
				default:
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
			}
		} catch (Exception e) {
			System.err.println("Error in ProductController: " + e.getMessage());
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process the request.");
		}
	}
	private boolean isCustomerSession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		return session != null && session.getAttribute("customer") != null;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String action = req.getServletPath();
			if ("/customer/cart/add".equals(action)) {
				addToCart(req, resp);
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println("Error in ProductController: " + e.getMessage());
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process the request.");
		}
	}
	 private void loadCommonAttributes(HttpServletRequest req) throws Exception {
	        req.setAttribute("categories", categoryService.getAllCategoriesName());
	        req.setAttribute("colors", productService.getAllColors());
	        req.setAttribute("materials", productService.getAllMaterials());
	        req.setAttribute("heights", productService.getAllHeights());
	        req.setAttribute("lengths", productService.getAllLengths());
	    }


	private String[] getParameterValuesOrDefault(HttpServletRequest req, String paramName) {
	    String[] values = req.getParameterValues(paramName);
	    if (values == null || values.length == 0) {
	        return new String[0];  
	    }
	    return values;
	}
	private void getProductDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String idParam = req.getParameter("id");
			if (idParam == null || idParam.isEmpty()) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
				return;
			}

			int productId = Integer.parseInt(idParam);
			Product product = productService.getProductById(productId);
			if (product == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
				return;
			}
			List<Review> reviews = reviewService.getReviewsAll(productId);
			req.setAttribute("reviews", reviews);

			int page = 1;
			int pageSize = 4;
			String pageParam = req.getParameter("page");
			if (pageParam != null && !pageParam.isEmpty()) {
				try {
					page = Integer.parseInt(pageParam);
				} catch (NumberFormatException e) {
					page = 1;
				}
			}

			List<Product> relatedProducts = productService.findRelatedProductsByProductId(productId, page, pageSize);
			long totalRelatedProducts = productService.getTotalRelatedProducts(productId);
			int totalPages = (int) Math.ceil((double) totalRelatedProducts / pageSize);

			req.setAttribute("product", product);
			req.setAttribute("relatedProducts", relatedProducts);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("currentPage", page);

			req.getRequestDispatcher("/customer/views/product/product_detail.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve product details");
		}
	}

	private void searchProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String keyword = req.getParameter("keyword");
			String filterCategoryName = req.getParameter("categoryName");
			HttpSession session = req.getSession();
			session.setAttribute("keyword", keyword);
			session.setAttribute("categoryName", filterCategoryName);

			String colorsStr = (String) session.getAttribute("colorsAsString");
			String materialsStr = (String) session.getAttribute("materialsAsString");
			String heightsStr = (String) session.getAttribute("heightsAsString");
			String lengthsStr = (String) session.getAttribute("lengthsAsString");

			String[] colors = colorsStr != null ? colorsStr.split(",") : null;
			String[] materials = materialsStr != null ? materialsStr.split(",") : null;
			String[] heights = heightsStr != null ? heightsStr.split(",") : null;
			String[] lengths = lengthsStr != null ? lengthsStr.split(",") : null;
			session.removeAttribute("colorsAsString");
			session.removeAttribute("materialsAsString");
			session.removeAttribute("heightsAsString");
			session.removeAttribute("lengthsAsString");

			colors = getParameterValuesOrDefault(req, "color");
			materials = getParameterValuesOrDefault(req, "material");
			heights = getParameterValuesOrDefault(req, "height");
			lengths = getParameterValuesOrDefault(req, "length");

			String minPriceParam = req.getParameter("minPrice");
			String maxPriceParam = req.getParameter("maxPrice");
			Double minPrice = (minPriceParam != null && !minPriceParam.equals("null") && !minPriceParam.isEmpty())
					? Double.valueOf(minPriceParam)
					: null;
			Double maxPrice = (maxPriceParam != null && !maxPriceParam.equals("null") && !maxPriceParam.isEmpty())
					? Double.valueOf(maxPriceParam)
					: null;

			String filterPrice = (minPrice != null && maxPrice != null) ? minPrice + "-" + maxPrice : null;
			session.setAttribute("selectedColors", colors);
			session.setAttribute("selectedMaterials", materials);
			session.setAttribute("selectedHeights", heights);
			session.setAttribute("selectedLengths", lengths);
			session.setAttribute("minPrice", minPrice);
			session.setAttribute("maxPrice", maxPrice);

			// Get page and pageSize from request parameters
			int page = 1; // Default to page 1
			int pageSize = 12; // Default to 12 items per page

			String pageParam = req.getParameter("page");
			if (pageParam != null && !pageParam.isEmpty()) {
				try {
					page = Integer.parseInt(pageParam);
				} catch (NumberFormatException e) {
					page = 1; // Default to page 1 if invalid
				}
			}

			String sizeParam = req.getParameter("size");
			if (sizeParam != null && !sizeParam.isEmpty()) {
				try {
					pageSize = Integer.parseInt(sizeParam);
				} catch (NumberFormatException e) {
					pageSize = 12; // Default to 12 if invalid
				}
			}

			List<Product> initialProducts = productService.findByKeywordOrCategory(keyword, filterCategoryName);

			List<Long> productIds = initialProducts.stream()
					.map(product -> (long) product.getProductId())
					.collect(Collectors.toList());

			List<Product> filteredProducts = productService.applyFiltersAfterKeywordOrCategory(
					productIds, filterPrice, colors, materials, heights, lengths
			);

			long totalProducts = productService.getProductCount(
					filterCategoryName, keyword, filterPrice, colors, materials, heights, lengths
			);

			int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
			totalPages = totalPages > 0 ? totalPages : 1;

			int startIndex = (page - 1) * pageSize;
			int endIndex = Math.min(startIndex + pageSize, filteredProducts.size());
			List<Product> paginatedProducts = filteredProducts.subList(startIndex, endIndex);

			// Set attributes for the JSP
			req.setAttribute("keyword", keyword);
			req.setAttribute("categoryName", filterCategoryName);
			req.setAttribute("products", paginatedProducts);
			req.setAttribute("totalProducts", totalProducts);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("pageSize", pageSize);

			// Generate page numbers for pagination display
			List<Integer> pageNumbers = new ArrayList<>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
			req.setAttribute("pageNumbers", pageNumbers);

			// Forward to JSP
			req.getRequestDispatcher("/customer/views/product/product_search.jsp").forward(req, resp);

		} catch (Exception e) {
			System.err.println("Error during searchProducts: " + e.getMessage());
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to search products.");
		}
	}


	private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("customer") == null) {
				resp.sendRedirect(req.getContextPath() + "/customer/login");
				return;
			}
			User customer = (User) session.getAttribute("customer");
			int customerId = customer.getUserId(); // Get customer ID

			String typeIdParam = req.getParameter("SelectedTypeId");
			String quantityParam = req.getParameter("quantity");

			if (typeIdParam == null || quantityParam == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
				return;
			}
			int typeId = Integer.parseInt(typeIdParam);
			int quantity = Integer.parseInt(quantityParam);
			CartServiceImpl cartService = new CartServiceImpl();
			cartService.addProductToCart(customerId, typeId, quantity);
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write("Product added to cart successfully");
		} catch (NumberFormatException e) {
			System.err.println("Invalid parameter format: " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameter format");
		} catch (Exception e) {
			System.err.println("Error while adding product to cart: " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to add product to cart");
		}
	}
}
