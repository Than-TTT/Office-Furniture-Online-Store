package cnpm.ergo.service.interfaces;

import cnpm.ergo.entity.Cart;
import cnpm.ergo.entity.ProductType;
import cnpm.ergo.entity.CartItem;
import java.util.List;
public interface ICartService {
    void addProductToCart(int customerId, int typeId, int quantity);
    Cart getCustomerCart(int customerId);
    void addProductType(ProductType productType);
    List<CartItem> getCartItems(int cartId);
}
