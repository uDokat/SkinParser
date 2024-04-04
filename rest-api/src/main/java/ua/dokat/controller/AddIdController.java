package ua.dokat.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.dokat.exeption.ConflictException;
import ua.dokat.service.AddIdService;

@RestController
@Log4j
public class AddIdController {

    private final AddIdService addIdService;

    public AddIdController(AddIdService addIdService) {
        this.addIdService = addIdService;
    }

    @PutMapping("/api/parser/add")
    public ResponseEntity<String> addId(@RequestParam int skinId, @RequestParam int chatId){

        try {

            addIdService.processAddIdRequest(skinId, chatId);
            return ResponseEntity.status(HttpStatus.OK).body("Skin's on the record");

        }catch (ConflictException e){

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        }catch (Exception e){

            log.error("An error occurred while processing the request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");

        }
    }
}
