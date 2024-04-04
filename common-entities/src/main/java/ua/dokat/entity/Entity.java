package ua.dokat.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.dokat.entity.http.BadResponse;
import ua.dokat.entity.json.JsonEntity;

@Builder
@Getter
public class Entity<T> {

    private T entityObject;
    private HttpStatus status;
    private String message;

    /**
     * Returns true if the entity is not null and the response status is OK
     *
     * @return Boolean
     **/
    public boolean isValid(){
        return entityObject != null && status.equals(HttpStatus.OK);
    }

    public ResponseEntity<JsonEntity> toResponseEntity(){
        if (!isValid()) return ResponseEntity.status(status).body(BadResponse.builder().message(message).build());
        return ResponseEntity.status(status).body((JsonEntity) entityObject);
    }
}
