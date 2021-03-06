package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.model.Unit;

public interface UnitRepository {

    Long create(Unit unit);

    void update(Unit unit);

    Unit findById(Long id);

    void deleteById(Long id);
}
