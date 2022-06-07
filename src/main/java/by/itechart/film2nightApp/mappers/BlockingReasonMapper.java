package by.itechart.film2nightApp.mappers;

import by.itechart.film2nightApp.dto.BlockingReasonDto;
import by.itechart.film2nightApp.entity.BlockingReason;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlockingReasonMapper {

    BlockingReasonMapper INSTANCE = Mappers.getMapper(BlockingReasonMapper.class);

    BlockingReason toBlockingReason(BlockingReasonDto blockingReasonDto);

    BlockingReasonDto toBlockingReasonDto(BlockingReason blockingReason);
}
