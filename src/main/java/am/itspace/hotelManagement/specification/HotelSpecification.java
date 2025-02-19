package am.itspace.hotelManagement.specification;

import am.itspace.hotelManagement.model.Hotel;
import am.itspace.hotelManagement.model.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class HotelSpecification {

  private static final String ROOMS = "rooms";

  private HotelSpecification() {}

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithFreeWiFi = isFreeWiFi ->
      ((root, query, criteriaBuilder) -> {
        if (isFreeWiFi == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get("isFreeWiFi"), isFreeWiFi);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithSwimmingPool = isSwimmingPool ->
      ((root, query, criteriaBuilder) -> {
        if (isSwimmingPool == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get("isSwimmingPool"), isSwimmingPool);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithParking = isParking ->
      ((root, query, criteriaBuilder) -> {
        if (isParking == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get("isParking"), isParking);
      });

  public static final Function<Boolean, Specification<Hotel>> hasRoomWithFitnessCenter = isFitnessCenter ->
      ((root, query, criteriaBuilder) -> {
        if (isFitnessCenter == null) return null;
        Join<Hotel, Room> roomJoin = root.join(ROOMS, JoinType.INNER);
        return criteriaBuilder.equal(roomJoin.get("isFitnessCenter"), isFitnessCenter);
      });

}
