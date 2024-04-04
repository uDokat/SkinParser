package ua.dokat.entity.database;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_by_monitored_skins")
public class UsersByMonitoredSkinsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int skinId;
    private int chatId;

}
