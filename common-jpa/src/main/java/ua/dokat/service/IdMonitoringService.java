package ua.dokat.service;

import ua.dokat.entity.database.IdMonitoredSkinsEntity;
import ua.dokat.exeption.NullIdMonitoredSkinsEntityException;

public interface IdMonitoringService {
    IdMonitoredSkinsEntity findList();
    boolean isRecord(int skinId);
    void addId(int skinId) throws NullIdMonitoredSkinsEntityException;
    void saveEntity(IdMonitoredSkinsEntity entity);
}
