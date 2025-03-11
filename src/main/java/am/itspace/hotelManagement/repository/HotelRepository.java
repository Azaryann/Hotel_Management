package am.itspace.hotelManagement.repository;


import am.itspace.hotelManagement.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
  Page<Hotel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
