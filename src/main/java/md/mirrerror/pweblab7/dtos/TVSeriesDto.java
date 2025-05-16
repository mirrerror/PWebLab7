package md.mirrerror.pweblab7.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TVSeriesDto {

    private Long id;
    private String title;
    private String genre;
    private String status;
    private String imageLink;
    private String link;
    private Integer rating;

}