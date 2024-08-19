package example.jpa.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class SerialId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
