package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.AwardDto;
import by.itechart.film2nightApp.dto.AwardSessionDto;
import by.itechart.film2nightApp.entity.Award;
import by.itechart.film2nightApp.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AwardMapper {

    AwardMapper INSTANCE = Mappers.getMapper(AwardMapper.class);

    Award toAward(AwardDto awardDto);

    AwardDto toAwardDto(Award award);

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.creator.username", target = "username")
    @Mapping(source = "award.id", target = "id")
    @Mapping(source = "award.name", target = "awardName")
    AwardSessionDto toAwardSessionDto(Session session, Award award);
}
