package md.mirrerror.pweblab7.dtos.mappers;

import md.mirrerror.pweblab7.dtos.TVSeriesCreationDto;
import md.mirrerror.pweblab7.models.TVSeries;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TVSeriesCreationMapper implements IMapper<TVSeriesCreationDto, TVSeries> {

    @Override
    public TVSeriesCreationDto mapToDto(TVSeries entity) {
        TVSeriesCreationDto dto = new TVSeriesCreationDto();
        dto.setTitle(entity.getTitle());
        dto.setGenre(entity.getGenre());
        dto.setStatus(entity.getStatus());
        dto.setLink(entity.getLink());
        dto.setImageLink(entity.getImageLink());
        dto.setRating(entity.getRating());
        return dto;
    }

    @Override
    public TVSeries mapToEntity(TVSeriesCreationDto dto) {
        TVSeries entity = new TVSeries();
        entity.setTitle(dto.getTitle());
        entity.setGenre(dto.getGenre());
        entity.setStatus(dto.getStatus());
        entity.setLink(dto.getLink());
        entity.setImageLink(dto.getImageLink());
        entity.setRating(dto.getRating());
        return entity;
    }

    @Override
    public List<TVSeriesCreationDto> mapToDtoList(List<TVSeries> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<TVSeries> mapToEntityList(List<TVSeriesCreationDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .toList();
    }

}
