package by.itechart.film2nightApp.service.impl;

import by.itechart.film2nightApp.dto.BlockingReasonDto;
import by.itechart.film2nightApp.entity.BlockingReason;
import by.itechart.film2nightApp.mappers.BlockingReasonMapperImpl;
import by.itechart.film2nightApp.repository.BlockingReasonRepository;
import by.itechart.film2nightApp.service.BlockingReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@Slf4j
public class BlockingReasonServiceImpl implements BlockingReasonService {

    private final BlockingReasonRepository blockingReasonRepository;

    @Autowired
    public BlockingReasonServiceImpl(BlockingReasonRepository blockingReasonRepository) {
        this.blockingReasonRepository = blockingReasonRepository;
    }

    @Override
    public Map<String, String> saveBlockingReason(BlockingReasonDto blockingReasonDto) {
        BlockingReason blockingReason = BlockingReasonMapperImpl.INSTANCE.toBlockingReason(blockingReasonDto);
        blockingReasonRepository.save(blockingReason);
        Map<String, String> response = new HashMap<>();
        response.put("message", blockingReason.getMessage());
        return response;
    }

    @Override
    public List<BlockingReason> getBlockingReasons() {
        return blockingReasonRepository.findAll();
    }

    @Override
    public BlockingReason findById(long id) {
        return blockingReasonRepository.findById(id).get();
    }
}
