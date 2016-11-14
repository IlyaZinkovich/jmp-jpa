package com.epam.jmp.jpa.service.impl;

import com.epam.jmp.jpa.model.Unit;
import com.epam.jmp.jpa.repository.UnitRepository;
import com.epam.jmp.jpa.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public Long create(Unit unit) {
        return unitRepository.create(unit);
    }

    @Override
    public void update(Unit unit) {
        unitRepository.update(unit);
    }

    @Override
    public Unit findById(Long id) {
        return unitRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        unitRepository.deleteById(id);
    }
}
