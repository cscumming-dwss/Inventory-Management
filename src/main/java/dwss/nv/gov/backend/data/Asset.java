package dwss.nv.gov.backend.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "Asset")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Asset.findAll", query = "SELECT p FROM Asset p"),
  @NamedQuery(name = "Asset.findAllBudgetCodes", query = "SELECT DISTINCT p.budgetCode From Asset p "),
  @NamedQuery(name = "Asset.findByAssetID", query = "SELECT p FROM Asset p WHERE p.id = :id"),
  @NamedQuery(name = "Asset.findByManufacturer", query = "SELECT p FROM Asset p WHERE p.manufacturer = :manufacturer"),
//  @NamedQuery(name = "Asset.findByBarCode", query = "SELECT p FROM Asset p WHERE p.barCode = :barCode"),
  @NamedQuery(name = "Asset.findByPropertyTag", query = "SELECT p FROM Asset p WHERE p.propertyTag = :propertyTag")})

public class Asset implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	// #ISSUE 18 ID not generatred 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = -1;
    //@OneToMany
    //private Set<Category> category;
	
	@Column(name="Barcode", columnDefinition = "NVarChar(50)")
	@NotNull
	// Issue #21 Constraint Violation
    private String barCode = new String();
	
	@Column(name="Propertytag", columnDefinition = "NVarChar(50)")
    private String propertyTag = new String();

	@Column(name = "Dateentered")
//	@NotNull
    private Date dateEntered = new Date();

	@Column(name = "Serial")
	@NotNull
    private String serialCode = new String();
	
	@Column(name="Officecode", columnDefinition = "NVarChar(50)")	
    private String office = new String();
	
	@Column(name="Description", columnDefinition = "nvarchar")	
	private String description = new String();
	
	@Column(name="Type", columnDefinition = "nvarchar")	
    private String assetType = new String();

	@Column(name="Model", columnDefinition = "nvarchar")	
	private String assetModel = new String();
	
	private String manufacturer = new String();
	
	private String operator = new String();
	
    private String unit = new String();

    private String comments = new String();    
	@Min(0)
    private BigDecimal cost = BigDecimal.ZERO;

	@Column(name="Showcomments", columnDefinition = "nvarchar")	
    private Boolean showComments = false;
    
    private String vendor = new String();

    private String tech = new String();

	@Column(name="datereceived", columnDefinition = "nvarchar")	
  //  @NotNull
    private Date dateReceived = new Date();

	@Column(name="purchaseorder", columnDefinition = "nvarchar")	
	private String purchaseOrder = new String();


	@Column(name="budgetcode", columnDefinition = "nvarchar")	
	private String budgetCode = new String();

    private String LU = null;

	@Column(name="Lastverified")	
//	@NotNull
    private Date verifiedDate = new Date();

	@Column(name="Computerrelated")	
  //  @NotNull
    private Boolean computerRelated = true;

    private Boolean excessed = false;

	@Column(name="locationcode", columnDefinition = "nvarchar")	
	private String locationCode;
	
	@Column(name="locationtype", columnDefinition = "nvarchar")	
	private String locationType = new String();
	
	@Column(name="replacementapproved")	
//    @NotNull
    private Date repApproved = new Date();
    
	@Column(name="Itemreplaced")	
    private Boolean itemReplaced = false;
    
	@Column(name="Notes", columnDefinition = "nvarchar")	
    private String notes = new String();

	@Column(name="Lastinventoried")
 //   @NotNull
    private Date inventoryDate = new Date();

	@Column(name="MarkedIS")
    private Boolean isEquipment = true;
	
	@Column(name="Actionlog", columnDefinition = "nvarchar")	
	private String historyLog = new String();
	
	@Column(name="heatticket", columnDefinition = "nvarchar")	
    private Integer heatTicket = 0;
    
    
 /*  
    
//    protected dwss.nv.gov.NumberField stockCount;
        
    //protected CategoryField category change to office code re example code
    //protected OfficeField officeCode;

  *  
   */ 
    
    public Asset() {
	}

    public Asset(Integer productID) {
        this.id = productID;
    }

	public Asset(Integer id, String barCode, String serialCode, String pTag, String manufacturer) {
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


/*    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }
*/
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

	public Integer getHeatTicket() {
		return heatTicket;
	}

	public void setHeatTicket(Integer heatTicket) {
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getShowComments() {
		return showComments;
	}

	public void setShowComments(Boolean showComments) {
		this.showComments = showComments;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getLU() {
		return LU;
	}

	public void setLU(String lU) {
		LU = lU;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(String budgetAccount) {
		this.budgetCode = budgetAccount;
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

	public Date getRepApproved() {
		return repApproved;
	}

	public void setRepApproved(Date repApproved) {
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
		if (!(obj instanceof Asset)) {
			return false;
		}
		Asset other = (Asset) obj;
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
