package cnpm.ergo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "voucher")
@NamedQuery(name="Voucher.findAll", query="select c from Voucher c")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voucherId;

    private Date dateStart;

    private Date dateEnd;

    private String code;

    private double discount;

    private boolean isDelete;

    @OneToOne(mappedBy = "voucher", cascade = CascadeType.MERGE)
    private MarketingCampaign marketingCampaign;
}
