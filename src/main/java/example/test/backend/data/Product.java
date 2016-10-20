package example.test.backend.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "Product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductID", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"),
    @NamedQuery(name = "Product.findByBarCode", query = "SELECT p FROM Product p WHERE p.barCode = :barCode")})
    

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Integer id;
    @NotNull
    @Size(min = 2, message = "Product name must have at least two characters")
    private String productName = "";
    @Min(0)
    private BigDecimal price = BigDecimal.ZERO;
    @OneToMany
    private Set<Category> category;
    @Min(value = 0, message = "Can't have negative amount in stock")
    private int stockCount = 0;
    @NotNull
    private Availability availability = Availability.COMING;
    @Size(max = 8, message = "Bar Code must not exceed 8 characters")
    private String barCode = null;
    @Size(max = 12, message = "Property Tag must not exceed 12 characters")
    private String propertyTag = null;
    @Size(max = 25, message = "Serial Code must not exceed 25 characters")
    private String serialCode = null;
    private String assetType = null;
    private String assetModel = null;
    private String manufacturer = null;
    private String description = null;
    
    private Date dateEntered = null;
    private Boolean itemReplaced = false;
    
    public Product() {
	}

    public Product(Integer productID) {
        this.id = productID;
    }

	public Product(Integer id, String productName, String barCode) {
		this.id = id;
		this.productName = productName;
		this.barCode = barCode;
	}    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getPropertyTag() {
		return propertyTag;
	}

	public void setPropertyTag(String propertyTag) {
		this.propertyTag = propertyTag;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetModel() {
		return assetModel;
	}

	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateEntered() {
		return dateEntered;
	}

	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	public Boolean isItemReplaced() {
		return itemReplaced;
	}

	public Boolean getItemReplaced() {
		return itemReplaced;
	}

	public void setItemReplaced(Boolean itemReplaced) {
		this.itemReplaced = itemReplaced;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
