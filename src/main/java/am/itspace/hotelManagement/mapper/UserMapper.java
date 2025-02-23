package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.UserDto;
import am.itspace.hotelManagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDTO(User user);

}
