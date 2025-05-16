package md.mirrerror.pweblab7.dtos.mappers;

import md.mirrerror.pweblab7.dtos.TVSeriesDto;
import md.mirrerror.pweblab7.models.TVSeries;
import md.mirrerror.pweblab7.models.TVSeriesStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TVSeriesMapper implements IMapper<TVSeriesDto, TVSeries> {

    @Override
    public TVSeriesDto mapToDto(TVSeries entity) {
        if (entity == null) return null;

        TVSeriesDto dto = new TVSeriesDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setGenre(entity.getGenre());
        dto.setStatus(entity.getStatus().name());
        dto.setLink(entity.getLink());
        dto.setImageLink(entity.getImageLink());
        dto.setRating(entity.getRating());

        return dto;
    }

    @Override
    public TVSeries mapToEntity(TVSeriesDto dto) {
        if (dto == null) return null;

        TVSeries entity = new TVSeries();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setGenre(dto.getGenre());
        entity.setStatus(TVSeriesStatus.valueOf(dto.getStatus()));
        entity.setLink(dto.getLink());
        entity.setImageLink(dto.getImageLink());
        entity.setRating(dto.getRating());

        return entity;
    }

    @Override
    public List<TVSeriesDto> mapToDtoList(List<TVSeries> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<TVSeries> mapToEntityList(List<TVSeriesDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .toList();
    }

}
