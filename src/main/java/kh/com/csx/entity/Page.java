package kh.com.csx.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pages")
public class Page {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_kh")
    private String titleKh;

    @Column(name = "url")
    private String url;

    @Column(name = "path")
    private String path;
    @Lob
    @Column(name = "description_en", columnDefinition = "LONGTEXT")
    private String descriptionEn;
    @Lob
    @Column(name = "description_kh", columnDefinition = "LONGTEXT")
    private String descriptionKh;

    @Column(name = "page_component")
    private String pageComponent;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
