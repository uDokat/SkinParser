package ua.dokat.service;

import ua.dokat.entity.database.UsersByMonitoredSkinsEntity;

import java.util.List;

public interface UsersByMonitoredSkinsService {
    List<UsersByMonitoredSkinsEntity> findIds(int skinId);
    boolean isRecord(int skinId, int chatId);
    void createAndSaveEntity(int skinId, int chatId);
    void saveEntity(UsersByMonitoredSkinsEntity entity);
    UsersByMonitoredSkinsEntity createEntity(int skinId, int chatId);
}
