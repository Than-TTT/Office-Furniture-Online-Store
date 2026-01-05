package cnpm.ergo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "blog")
@NamedQuery(name = "Blog.findAll", query = "SELECT b FROM Blog b")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blogId")
    private int blogId;

    @Column(name = "postingDate", columnDefinition = "DATE NOT NULL")
    private LocalDate postingDate;

    @Column(name = "blogTitle", columnDefinition = "NVARCHAR(200) NOT NULL")
    private String blogTitle;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "approval", columnDefinition = "BIT")
    private boolean approval;

}
