package dwss.nv.gov.backend.data;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private int id = -1;

	@NotNull
	@AttributeOverride(name = "Username", column = @Column(name = "Username"))
    private String username = null;

	@NotNull
	@Column(name = "PermissionLevel")
    private String permissionLevel = null;

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permissionLevel == null) ? 0 : permissionLevel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + id;
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
		User other = (User) obj;
		if (permissionLevel == null) {
			if (other.permissionLevel != null)
				return false;
		} else if (!permissionLevel.equals(other.permissionLevel))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
    public String toString() {
        return getUserName() + " " + getPermissionLevel() + " " + getId();
    }
}
