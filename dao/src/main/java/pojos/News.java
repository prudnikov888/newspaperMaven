package pojos;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class describes News_entity
 */
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News implements Serializable {
    private static final long serialVersionUID = 2L;

    public News() {
    }
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int newsId;
    @Column
    private String title;
    @Column
    private String title4ann;
    @Column
    private String author;
    @Column
    private String postDay;
    @Column
    @Type(type="text")
    private String mainText;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn (name = "user")
    private Users user;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn (name = "category")
    private Category category;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle4ann() {
        return title4ann;
    }

    public void setTitle4ann(String title4ann) {
        this.title4ann = title4ann;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostDay() {
        return postDay;
    }

    public void setPostDay(String postDay) {
        this.postDay = postDay;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Category getCategory() {return category;}

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof News) {
            News temp = (News) obj;
            if (newsId == temp.newsId
                    && title.equals(temp.title)
                    && title4ann.equals(temp.title4ann)
                    && author.equals(temp.author)
                    && postDay.equals(temp.postDay)
                    && mainText.equals(temp.mainText))
                return true;
            else
                return false;
        } else
            return false;
    }
    @Override
    public int hashCode() {
        String s = newsId + "";
        return s.hashCode();

    }
}
