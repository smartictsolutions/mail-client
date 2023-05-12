/* SmartICT Bilisim A.S. (C) 2022 */
package com.smartict.mail.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.smartict.mail.constant.exceptions.EnumCrudExceptionMessages;
import com.smartict.mail.constant.exceptions.ServiceException;
import com.smartict.mail.constant.messages.EnumExceptionMessages;
import com.smartict.mail.db.dao.BaseRepository;
import com.smartict.mail.service.BaseService;
import com.smartict.mail.service.impl.mapper.base.BaseMapper;

import org.springframework.beans.factory.annotation.Qualifier;

public class BaseServiceImpl<D, E> implements BaseService<D> {
    protected final BaseRepository<E, UUID> baseRepository;

    private final BaseMapper<E, D> baseMapper;

    public BaseServiceImpl(@Qualifier("BaseRepository") BaseRepository<E, UUID> baseRepository, BaseMapper<E, D> baseMapper) {
        this.baseRepository = baseRepository;
        this.baseMapper = baseMapper;
    }

    @Override
    public D create(D dto) {
        return baseMapper.entityToDto(baseRepository.save(baseMapper.dtoToEntity(dto)));
    }

    @Override
    public D update(D dto) {
        try {
            Field field = dto.getClass().getDeclaredField("id");
            field.setAccessible(true);
            if (Objects.isNull(field.get(dto))) {
                throw new ServiceException(
                    EnumCrudExceptionMessages.ID_NOT_BE_EMPTY.getLanguageKey(),
                    EnumCrudExceptionMessages.ID_NOT_BE_EMPTY.getLanguageValue()
                );
            }
            return baseMapper.entityToDto(baseRepository.save(baseMapper.dtoToEntity(dto)));
        } catch (NoSuchFieldException e) {
            throw new ServiceException(
                EnumCrudExceptionMessages.FIELD_NOT_FOUND.getLanguageKey(),
                EnumCrudExceptionMessages.FIELD_NOT_FOUND.getLanguageValue()
            );
        } catch (Exception e) {
            throw new ServiceException(
                EnumExceptionMessages.UNEXPECTED_ERROR_OCCURRED.getLanguageKey(),
                EnumExceptionMessages.UNEXPECTED_ERROR_OCCURRED.getLanguageValue()
            );
        }
    }

    @Override
    public void delete(UUID id) {
        E result = baseRepository.getById(id);
        baseRepository.delete(result);
    }

    @Override
    public D read(UUID id) {
        return baseMapper.entityToDto(baseRepository.getById(id));
    }

    @Override
    public List<D> readAll() {
        return baseMapper.entityListToDtoList(baseRepository.findAll());
    }
}
