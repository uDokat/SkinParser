package ua.dokat.entity.database;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ua.dokat.entity.json.IdListJson;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list_monitored_skins")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class IdMonitoredSkinsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private IdListJson ids;
}
