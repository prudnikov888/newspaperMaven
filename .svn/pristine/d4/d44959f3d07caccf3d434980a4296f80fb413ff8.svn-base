package pojos;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * This class describes User_entity
 */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    public Users() {
    }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int userId;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String pass;

    @OneToMany (mappedBy = "user")
    private Set<News> news;

    @ManyToMany (mappedBy = "users")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Roles> roles;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Users) {
            Users temp = (Users) obj;
            if (userId == temp.userId
                    && name.equals(temp.name)
                    && surname.equals(temp.surname)
                    && email.equals(temp.email)
                    && pass.equals(temp.pass))
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        String s = userId + "";
        return s.hashCode();
    }



}
