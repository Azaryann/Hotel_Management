package am.itspace.hotelManagement.mapper;

import am.itspace.hotelManagement.dto.CreateUserDto;
import am.itspace.hotelManagement.dto.UpdateUserDto;
import am.itspace.hotelManagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UpdateUserDto toUpdateDTO(User user);

    CreateUserDto toCreateDto(User user);
}
