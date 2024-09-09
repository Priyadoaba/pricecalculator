package no.sample.pricecalculator.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "watch")
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String watchId;
    @NotBlank
    private String watchName;
    @NotBlank
    private long unitPrice;
    private int discountOnQuantity;
    private long discountedPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getWatchName() {
        return watchName;
    }

    public void setWatchName(String watchName) {
        this.watchName = watchName;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(@Nullable long discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getDiscountOnQuantity() {
        return discountOnQuantity;
    }

    public void setDiscountOnQuantity(@Nullable int discountOnQuantity) {
        this.discountOnQuantity = discountOnQuantity;
    }
}
