package cnpm.ergo.entity;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@NamedQuery(
        name = "VoucherByPrice.findAllActive",
        query = "SELECT c FROM VoucherByPrice c where c.isDelete = false "
)

@PrimaryKeyJoinColumn(name = "voucherByPriceId")
@NamedQuery(name = "VoucherByPrice.findAll", query = "SELECT v FROM VoucherByPrice v")

public class VoucherByPrice extends Voucher {
    private double lowerbound;

	@Override
	public String toString() {
		return "VoucherByPrice [lowerbound=" + lowerbound + "]";
	}

    
}
