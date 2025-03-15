package am.itspace.hotelManagement.test_factories;

import am.itspace.hotelManagement.dto.UpdateUserDto;

public class UserTestDataFactory {

    public static UpdateUserDto getUpdateDto(){
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("aaa");
        updateUserDto.setLastName("bbb");
        updateUserDto.setEmail("aaabbb@example.com");
        updateUserDto.setPassword("password");
        updateUserDto.setRole("USER");
        return updateUserDto;
    }

}
