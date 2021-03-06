package pojos;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * This class describes Category_entity
 */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {
    private static final long serialVersionUID = 4L;
    public Category() {
    }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column
    private String categoryName;

    @OneToMany (mappedBy = "category")
    private Set<News> news;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category temp = (Category) obj;
            if (categoryId == temp.categoryId && categoryName.equals(temp.categoryName))
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public int hashCode() {
        String s = categoryId + "";
        return s.hashCode();
    }
}

