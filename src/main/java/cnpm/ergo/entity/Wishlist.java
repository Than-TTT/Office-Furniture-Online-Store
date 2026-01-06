package cnpm.ergo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Wishlist")
public class Wishlist {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlistId")
    private int wishlistId;

    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "wishlist_product",
			joinColumns = @JoinColumn(name = "wishlistId"),
			inverseJoinColumns = @JoinColumn(name = "productId")
	)
	private List<Product> products;

    @OneToOne
    @JoinColumn(name = "customerId",  referencedColumnName = "customerId")
    private Customer customer;

    @Column(name = "isDelete", columnDefinition = "BIT", nullable = false)
    private boolean isDelete;


    
    

}
