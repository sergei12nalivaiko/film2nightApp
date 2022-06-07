package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.AuthenticationRequestDTO;
import by.itechart.film2nightApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AuthenticationRequestDTO toAuthenticationRequestDTO(User user);

    User user(AuthenticationRequestDTO authenticationRequestDTO);
}
