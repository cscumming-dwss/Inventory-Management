package example.test.backend.data;

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
@Table(name = "Vendor")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Vendor.findAll", query = "SELECT m FROM Vendor m"),
  @NamedQuery(name = "Vendor.findByVendorID", query = "SELECT m FROM Vendor m WHERE m.id = :id"),
  @NamedQuery(name = "Vendor.findByEntry", query = "SELECT m FROM Vendor m WHERE m.entry = :entry")})


public class Vendor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	//@OneToMany 
	//@JoinColumn(id);
	private Integer id = -1;
	
	@Column(name="Entry", columnDefinition = "NVarChar(50)")
	@NotNull
    private String entry = new String();


	public Vendor() {
		
	}
	
	
	public Vendor(Integer id, String entry) {
		this.id = id;
		this.entry = entry;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEntry() {
		return entry;
	}


	public void setEntry(String entry) {
		this.entry = entry;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendor other = (Vendor) obj;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Vendor [id=" + id + ", entry=" + entry + "]";
	}

}
