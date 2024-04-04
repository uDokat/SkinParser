package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ua.dokat.dao.UsersByMonitoredSkinsDAO;
import ua.dokat.entity.database.UsersByMonitoredSkinsEntity;
import ua.dokat.service.UsersByMonitoredSkinsService;

import java.util.List;

@Service
@Log4j
public class UsersByMonitoredSkinsServiceImpl implements UsersByMonitoredSkinsService {

    private final UsersByMonitoredSkinsDAO usersByMonitoredSkinsDAO;

    public UsersByMonitoredSkinsServiceImpl(UsersByMonitoredSkinsDAO usersByMonitoredSkinsDAO) {
        this.usersByMonitoredSkinsDAO = usersByMonitoredSkinsDAO;
    }

    @Override
    public List<UsersByMonitoredSkinsEntity> findIds(int skinId) {
        return usersByMonitoredSkinsDAO.findBySkinId(skinId);
    }

    @Override
    public boolean isRecord(int skinId, int chatId) {
        return findIds(skinId).stream().anyMatch(entity -> entity.getChatId() == chatId);
    }

    @Override
    public void createAndSaveEntity(int skinId, int chatId) {
        saveEntity(createEntity(skinId, chatId));
    }

    @Override
    public void saveEntity(UsersByMonitoredSkinsEntity entity) {
        try {
            usersByMonitoredSkinsDAO.save(entity);
        }catch (Exception e){
            log.error("Failed to save UsersByMonitoredSkinsEntity", e);
        }
    }

    @Override
    public UsersByMonitoredSkinsEntity createEntity(int skinId, int chatId) {
        return UsersByMonitoredSkinsEntity.builder()
                .skinId(skinId)
                .chatId(chatId)
                .build();
    }
}
