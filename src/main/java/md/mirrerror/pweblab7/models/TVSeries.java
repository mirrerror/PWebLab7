package md.mirrerror.pweblab7.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "series")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TVSeries {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "status")
    private TVSeriesStatus status;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "link")
    private String link;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
