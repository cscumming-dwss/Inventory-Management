package example.test.backend.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Asset implements Serializable {

    @NotNull
    private int id = -1;
    @NotNull
    @Min(100) 
    private BigInteger barCode = BigInteger.ZERO;
    
    public BigInteger getBarCode() {
		return barCode;
	}

	public void setBarCode(BigInteger barCode) {
		this.barCode = barCode;
	}

	@Min(0)
    private BigDecimal cost = BigDecimal.ZERO;
    private Set<Category> category;
    @Min(value = 0, message = "Can't have negative amount in stock")
    private int stockCount = 0;
    @NotNull
    private Availability availability = Availability.COMING;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

}
