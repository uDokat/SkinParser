package ua.dokat.service;

import ua.dokat.exeption.ConflictException;

public interface AddIdService {
    void processAddIdRequest(int skinId, int chatId) throws ConflictException;
}
