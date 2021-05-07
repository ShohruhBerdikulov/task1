package ai.ecma.task1.repository;

import ai.ecma.task1.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);
}
