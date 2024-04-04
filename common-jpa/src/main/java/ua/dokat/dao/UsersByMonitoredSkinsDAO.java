package ua.dokat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dokat.entity.database.UsersByMonitoredSkinsEntity;

import java.util.List;

public interface UsersByMonitoredSkinsDAO extends JpaRepository<UsersByMonitoredSkinsEntity, Long> {
    List<UsersByMonitoredSkinsEntity> findBySkinId(int skinId);
}
