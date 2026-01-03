package model;

import java.util.Date;

public class VoucherDto {
    private double lowerbound;
    private int voucherByPriceId;
    private int voucherId;
    private Date dateStart;
    private Date dateEnd;
    private String code;
    private double discount;

    public VoucherDto(double lowerbound, int voucherByPriceId, int voucherId, Date dateStart, Date dateEnd, String code, double discount) {
        this.lowerbound = lowerbound;
        this.voucherByPriceId = voucherByPriceId;
        this.voucherId = voucherId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.code = code;
        this.discount = discount;
    }
    public VoucherDto() {
		super();
	}


	// Getters and setters
    public double getLowerbound() {
        return lowerbound;
    }

    public void setLowerbound(double lowerbound) {
        this.lowerbound = lowerbound;
    }

    public int getVoucherByPriceId() {
        return voucherByPriceId;
    }

    public void setVoucherByPriceId(int voucherByPriceId) {
        this.voucherByPriceId = voucherByPriceId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

	@Override
	public String toString() {
		return "VoucherDto [lowerbound=" + lowerbound + ", voucherByPriceId=" + voucherByPriceId + ", voucherId="
				+ voucherId + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", code=" + code + ", discount="
				+ discount + "]";
	}
    
    
}
