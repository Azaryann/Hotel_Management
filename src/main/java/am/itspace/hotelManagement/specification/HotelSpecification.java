package am.itspace.hotelManagement.specification;

import am.itspace.hotelManagement.entity.Hotel;
import am.itspace.hotelManagement.entity.Room;
import am.itspace.hotelManagement.enums.Rate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Function;

public class HotelSpecification {

  private static final String ROOMS = "rooms";
  private static final String IS_FREE_WIFI ="isFreeWiFi";
  private static final String IS_SWIMMING_POOL = "isSwimmingPool";
  private static final String IS_PARKING = "isParking";
  private static final String IS_FITNESS_CENTER = "isFitnessCenter";
  private static final String NAME = "name";

  private HotelSpecification() {}

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithFreeWiFi = isFreeWiFi ->
      ((root, query, criteriaBuilder) -> {
        if (isFreeWiFi == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get(IS_FREE_WIFI), isFreeWiFi);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithSwimmingPool = isSwimmingPool ->
      ((root, query, criteriaBuilder) -> {
        if (isSwimmingPool == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get(IS_SWIMMING_POOL), isSwimmingPool);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithParking = isParking ->
      ((root, query, criteriaBuilder) -> {
        if (isParking == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get(IS_PARKING), isParking);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithFitnessCenter = isFitnessCenter ->
      ((root, query, criteriaBuilder) -> {
        if (isFitnessCenter == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get(IS_FITNESS_CENTER), isFitnessCenter);
      });

  public static final Function<List<Rate>, Specification<Hotel>> hasRate = rates ->
      ((root, query, criteriaBuilder) -> {
        if (rates == null || rates.isEmpty()) return criteriaBuilder.conjunction();
        return root.get("rate").in(rates);
      });

  public static final Function<String, Specification<Hotel>> searchHotel = name ->
      ((root, query, criteriaBuilder) -> {
        if (name == null || name.trim().isEmpty()) return criteriaBuilder.conjunction();
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), "%" + name.toLowerCase() + "%");
      });

}
