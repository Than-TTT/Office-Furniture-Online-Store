package model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@NamedQuery(
        name = "VoucherByProduct.findAll",
        query = "SELECT c FROM VoucherByProduct c where c.isDelete = false"
)
@PrimaryKeyJoinColumn(name = "voucherByProductId")
@NamedQuery(name = "VoucherByProduct.findAll", query = "SELECT v FROM VoucherByProduct v")



public class VoucherByProduct extends Voucher {
    @ManyToMany
    @JoinTable(
            name = "voucher_product_type",
            joinColumns = @JoinColumn(name = "voucherByProductId"),
            inverseJoinColumns = @JoinColumn(name = "typeId")
    )
    private List<ProductType> productTypes;
	@Override
	public String toString() {
		return "VoucherByProduct [productTypes=" + productTypes + "]";
	}
}

