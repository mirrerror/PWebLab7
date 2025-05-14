package md.mirrerror.pweblab7.dtos.mappers;

import java.util.List;

public interface IMapper<T, U> {

    T mapToDto(U entity);
    U mapToEntity(T dto);
    List<T> mapToDtoList(List<U> entityList);
    List<U> mapToEntityList(List<T> dtoList);

}
