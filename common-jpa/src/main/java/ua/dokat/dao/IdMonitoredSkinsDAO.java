package ua.dokat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dokat.entity.database.IdMonitoredSkinsEntity;

import java.util.List;


public interface IdMonitoredSkinsDAO extends JpaRepository<IdMonitoredSkinsEntity, Long> {
    List<IdMonitoredSkinsEntity> findAll();
}
