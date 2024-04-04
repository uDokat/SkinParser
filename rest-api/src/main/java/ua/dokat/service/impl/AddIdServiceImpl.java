package ua.dokat.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ua.dokat.exeption.ConflictException;
import ua.dokat.exeption.NullIdMonitoredSkinsEntityException;
import ua.dokat.service.AddIdService;
import ua.dokat.service.IdMonitoringService;
import ua.dokat.service.UsersByMonitoredSkinsService;

@Service
@Log4j
public class AddIdServiceImpl implements AddIdService {

    private final UsersByMonitoredSkinsService monitoredSkinsService;
    private final IdMonitoringService idMonitoringService;

    public AddIdServiceImpl(UsersByMonitoredSkinsService monitoredSkinsService, IdMonitoringService idMonitoringService) {
        this.monitoredSkinsService = monitoredSkinsService;
        this.idMonitoringService = idMonitoringService;
    }

    @Override
    public void processAddIdRequest(int skinId, int chatId) throws ConflictException {
        if (monitoredSkinsService.isRecord(skinId, chatId)) throw new ConflictException("A user has already credited this skin");

        try {

            addToListIfNoRecord(skinId);
            monitoredSkinsService.createAndSaveEntity(skinId, chatId);

        }catch (NullIdMonitoredSkinsEntityException e){

            log.error("Skin id cannot be added to the database. The list is not initialized", e);

        }
    }

    private void addToListIfNoRecord(int skinId) throws NullIdMonitoredSkinsEntityException {
        if (idMonitoringService.isRecord(skinId)) return;
        idMonitoringService.addId(skinId);
    }
}
