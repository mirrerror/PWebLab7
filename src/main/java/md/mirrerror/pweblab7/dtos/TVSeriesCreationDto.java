package md.mirrerror.pweblab7.dtos;

import lombok.Data;
import md.mirrerror.pweblab7.models.TVSeriesStatus;

@Data
public class TVSeriesCreationDto {

    private String title;
    private String genre;
    private TVSeriesStatus status;
    private String imageLink;
    private String link;
    private Integer rating;

}
