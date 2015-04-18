package pojos;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * This class describes Roles_entity
 */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Roles implements Serializable {
    private static final long serialVersionUID = 3L;

    public Roles() {
    }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column
    private String roleType;

    @ManyToMany
    @JoinTable
            (name = "T_Roles_Users", joinColumns = {@JoinColumn (name = "roleId")},
            inverseJoinColumns = {@JoinColumn (name = "userId")})
    private Set<Users> users;

    @OneToOne (mappedBy = "role")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Permissions permission;

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Roles) {
            Roles temp = (Roles) obj;
            if (roleId == temp.roleId && roleType.equals(temp.roleType))
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public int hashCode() {
        String s = roleId + "";
        return s.hashCode();
    }



}
