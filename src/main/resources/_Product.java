package dwss.nv.gov.backend.data;

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

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;


//@Entity
//@Table(name = "Product")
//@XmlRootElement
/*@NamedQueries({
//    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
//    @NamedQuery(name = "Product.findByProductID", query = "SELECT p FROM Product p WHERE p.id = :id"),
//    @NamedQuery(name = "Product.findByManufacturer", query = "SELECT p FROM Product p WHERE p.manufacturer = :manufacturer"),
//    @NamedQuery(name = "Product.findByBarCode", query = "SELECT p FROM Product p WHERE p.barCode = :barCode"),
//	  @NamedQuery(name = "Product.findByPropertyTag", query = "SELECT p FROM Product p WHERE p.propertyTag = :propertyTag")})
    
*/
/*public class _Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Integer id;
    //@OneToMany
    //private Set<Category> category;
    @Size(max = 25, message = "Bar Code must not exceed 8 characters")
    private String barCode = null;
    @Size(max = 25, message = "Property Tag must not exceed 12 characters")
    private String propertyTag = null;
    @Size(max = 25, message = "Serial Code must not exceed 25 characters")
    private String serialCode = null;
    private String manufacturer = null;
    private String assetModel = null;
    private String description = null;
    private String assetType = null;
    private String assetLocation = null;
    private String office = null;
    private Boolean computerRelated = null;
    private String unit = null;
    private String notes = null;
    private Boolean isEquipment = true;
    private String heatTicket = null;
    private Date verifiedDate = null;
    private Boolean excessed = false;
    private Date dateReceived = null;
    private String fourYearReplacement = null;
    private String budgetAccount = null;
    private String purchaseOrder = null;
    
    @Min(0)
    private BigDecimal cost = BigDecimal.ZERO;
    private String vendor = null;
    private String repApproved = null;
    private Date inventoryDate = null;
    private String comments = null;
    
    private String historyLog = null;
    private Date dateEntered = null;
    private Boolean itemReplaced = false;
    
    
   
    
//    protected dwss.nv.gov.NumberField stockCount;
        
    //protected CategoryField category change to office code re example code
    //protected OfficeField officeCode;

  *  
    
    
    
    public _Product() {
	}

    public _Product(Integer productID) {
        this.id = productID;
    }

	public _Product(Integer id, String barCode, String serialCode, String pTag, String manufacturer) {
		this.id = id;
		this.barCode = barCode;
		this.serialCode = serialCode;
		this.propertyTag = pTag;
		this.manufacturer = manufacturer;
		
	}    
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

	
	public String getAssetLocation() {
		return assetLocation;
	}

	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Boolean getComputerRelated() {
		return computerRelated;
	}

	public void setComputerRelated(Boolean computerRelated) {
		this.computerRelated = computerRelated;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean getIsEquipment() {
		return isEquipment;
	}

	public void setIsEquipment(Boolean isEquipment) {
		this.isEquipment = isEquipment;
	}

	public String getHeatTicket() {
		return heatTicket;
	}

	public void setHeatTicket(String heatTicket) {
		this.heatTicket = heatTicket;
	}

	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public Boolean getExcessed() {
		return excessed;
	}

	public void setExcessed(Boolean excessed) {
		this.excessed = excessed;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getFourYearReplacement() {
		return fourYearReplacement;
	}

	public void setFourYearReplacement(String fourYearReplacement) {
		this.fourYearReplacement = fourYearReplacement;
	}

	public String getBudgetAccount() {
		return budgetAccount;
	}

	public void setBudgetAccount(String budgetAccount) {
		this.budgetAccount = budgetAccount;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getRepApproved() {
		return repApproved;
	}

	public void setRepApproved(String repApproved) {
		this.repApproved = repApproved;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getHistoryLog() {
		return historyLog;
	}

	public void setHistoryLog(String actionLog) {
		this.historyLog = actionLog;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	 (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	 (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof _Product)) {
			return false;
		}
		_Product other = (_Product) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

*/}
