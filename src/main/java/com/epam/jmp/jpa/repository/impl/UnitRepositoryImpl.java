package com.epam.jmp.jpa.repository.impl;

import com.epam.jmp.jpa.model.Unit;
import com.epam.jmp.jpa.repository.UnitRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UnitRepositoryImpl implements UnitRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Unit unit) {
        entityManager.persist(unit);
        return unit.getId();
    }

    @Override
    public void update(Unit unit) {
        entityManager.merge(unit);
    }

    @Override
    public Unit findById(Long id) {
        return entityManager.find(Unit.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Unit unit = entityManager.find(Unit.class, id);
        if (unit != null) {
            entityManager.remove(unit);
        }
    }
}
