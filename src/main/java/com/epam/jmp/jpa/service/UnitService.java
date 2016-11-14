package com.epam.jmp.jpa.service;

import com.epam.jmp.jpa.model.Unit;

public interface UnitService {

    Long create(Unit unit);

    void update(Unit unit);

    Unit findById(Long id);

    void deleteById(Long id);
}
