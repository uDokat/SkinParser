package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ua.dokat.dao.IdMonitoredSkinsDAO;
import ua.dokat.entity.database.IdMonitoredSkinsEntity;
import ua.dokat.exeption.NullIdMonitoredSkinsEntityException;
import ua.dokat.service.IdMonitoringService;

import java.util.List;

@Service
@Log4j
public class IdMonitoringServiceImpl implements IdMonitoringService {

    private final IdMonitoredSkinsDAO idMonitoredSkinsDAO;

    public IdMonitoringServiceImpl(IdMonitoredSkinsDAO idMonitoredSkinsDAO) {
        this.idMonitoredSkinsDAO = idMonitoredSkinsDAO;
    }

    @Override
    public IdMonitoredSkinsEntity findList() {
        List<IdMonitoredSkinsEntity> entities = idMonitoredSkinsDAO.findAll();
        return entities.isEmpty() ? null : entities.get(0);
    }

    @Override
    public boolean isRecord(int skinId) {
        List<Integer> ids = findList().getIds().getIds();
        return ids != null && ids.contains(skinId);
    }

    @Override
    public void addId(int skinId) throws NullIdMonitoredSkinsEntityException {
        IdMonitoredSkinsEntity entity = findList();

        if (entity != null){
            entity.getIds().getIds().add(skinId);
            saveEntity(entity);
        }else {
            throw new NullIdMonitoredSkinsEntityException("IdMonitoredSkinsEntity id null");
        }
    }

    @Override
    public void saveEntity(IdMonitoredSkinsEntity entity) {
        try {
            idMonitoredSkinsDAO.save(entity);
        }catch (Exception e){
            log.error("Failed to save IdMonitoredSkinsEntity", e);
        }
    }
}
