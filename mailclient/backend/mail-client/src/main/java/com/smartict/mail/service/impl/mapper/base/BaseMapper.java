/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.mail.service.impl.mapper.base;

import java.util.List;

public interface BaseMapper<E, D> {
    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoListToEntityList(List<D> dtoList);

    List<D> entityListToDtoList(List<E> entityList);
}
