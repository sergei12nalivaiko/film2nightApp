package by.itechart.film2nightApp.service;

import by.itechart.film2nightApp.dto.BlockingReasonDto;
import by.itechart.film2nightApp.entity.BlockingReason;

import java.util.List;
import java.util.Map;

public interface BlockingReasonService {
    Map<String, String> saveBlockingReason(BlockingReasonDto blockingReasonDto);

    List<BlockingReason> getBlockingReasons();

    BlockingReason findById(long id);
}
