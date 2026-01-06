package cnpm.ergo.service.implement;

import cnpm.ergo.DAO.implement.CartDaoImpl;
import cnpm.ergo.DAO.implement.ProductTypeDaoImpl;
import cnpm.ergo.entity.Cart;
import cnpm.ergo.entity.Customer;
import cnpm.ergo.service.interfaces.ICartService;
import jakarta.persistence.EntityManager;
import cnpm.ergo.configs.JPAConfig;
import cnpm.ergo.entity.ProductType;
import cnpm.ergo.entity.CartItem;
import java.util.List;
public class CartServiceImpl implements ICartService {

    private final CartDaoImpl cartDao;
    private final ProductTypeDaoImpl productTypeDao;

    public CartServiceImpl() {
        this.cartDao = new CartDaoImpl();
        this.productTypeDao = new ProductTypeDaoImpl();
    }
    @Override
    public void addProductToCart(int customerId, int typeId, int quantity) {
        try {
            Cart cart = cartDao.getCartById(customerId);
            if (cart == null) {
                System.out.println("Giỏ hàng chưa tồn tại cho khách hàng ID: " + customerId);
                EntityManager em = JPAConfig.getEntityManager();
                Customer customer = em.find(Customer.class, customerId);
                if (customer == null) {
                    customer = new Customer();
                    customer.setUserId(customerId);
                    customer.setName("Customer " + customerId);
                    em.getTransaction().begin();
                    em.persist(customer);
                    em.getTransaction().commit();
                    System.out.println("Đã tạo khách hàng mới: " + customer.getName());
                }
                cartDao.createCart(customer);
                System.out.println("Đã tạo giỏ hàng cho khách hàng ID: " + customerId);
                cart = cartDao.getCartById(customerId);
            }
            cartDao.addToCart(customerId, typeId, quantity);
            System.out.println("Đã thêm " + quantity + " sản phẩm vào giỏ hàng của khách hàng ID " + customerId);

        } catch (Exception e) {
            System.err.println("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void addProductType(ProductType productType) {
        try {
            productTypeDao.insert(productType);
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm loại sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public Cart getCustomerCart(int customerId) {
        try {
            Cart cart = cartDao.getCartById(customerId);
            if (cart == null) {
                System.out.println("Giỏ hàng không tồn tại cho khách hàng ID: " + customerId);
            } else {
                System.out.println("Lấy giỏ hàng thành công cho khách hàng ID: " + customerId);
            }
            return cart;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy giỏ hàng: " + e.getMessage());
            return null;
        }
    }
    @Override
    public List<CartItem> getCartItems(int cartId) {
        try {
            List<CartItem> cartItems = cartDao.getCartItems(cartId);
            if (cartItems == null || cartItems.isEmpty()) {
                System.out.println("Giỏ hàng với ID " + cartId + " không chứa sản phẩm nào.");
            }
            return cartItems;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm từ giỏ hàng: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        CartServiceImpl cartService = new CartServiceImpl();
        CartDaoImpl cartDao = new CartDaoImpl();
        ProductType productType = new ProductType();
        productType.setTypeId(2);
        productType.setPrice(500.0);
        cartService.addProductType(productType);

        int customerId = 2;
        int typeId = productType.getTypeId();
        int quantity = 2;

        cartService.addProductToCart(customerId, typeId, quantity);

        var cart = cartService.getCustomerCart(customerId);
        if (cart != null && ! cartService.getCartItems(cart.getCartId()).isEmpty()) {
            System.out.println("Chi tiết giỏ hàng của khách hàng ID " + customerId + ":");
            cartService.getCartItems(cart.getCartId()).forEach(item -> {
                System.out.println("   Số lượng: " + item.getQuantity());
                System.out.println("   Giá: " + item.getPrice());
            });
        } else {
            System.out.println("Giỏ hàng của khách hàng ID " + customerId + " rỗng.");
        }
    }
}