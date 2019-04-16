package com.osuskin.cloud.dao;

import com.osuskin.cloud.pojo.dto.CommonDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SoftDeleteCrudRepository <T extends CommonDto, ID extends Long> extends CrudRepository<T, ID> {
    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.is_deleted = false ")
    List<T> findAll();

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id in ?1 and  e.is_deleted = false")
    Iterable<T> findAllById(Iterable<ID> ids);

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and  e.is_deleted = false")
    Optional<T> findById(ID id);

    //Look up deleted entities
    @Query("select e from #{#entityName} e where  e.is_deleted = true")
    @Transactional(readOnly = true)
    List<T> findInactive();

    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where  e.is_deleted = false")
    long count();

    @Override
    @Transactional(readOnly = true)
    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    @Query("update #{#entityName} e set e.is_deleted = true where e.id = ?1")
    @Transactional
    @Modifying
    void deleteById(Long id);


    @Override
    @Transactional
    default void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entitiy -> deleteById(entitiy.getId()));
    }

    @Override
    @Query("update #{#entityName} e set e.isActive=false")
    @Transactional
    @Modifying
    void deleteAll();
}
