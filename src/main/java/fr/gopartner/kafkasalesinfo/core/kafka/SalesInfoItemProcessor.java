package fr.gopartner.kafkasalesinfo.core.kafka;

import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfo;
import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfoDto;
import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SalesInfoItemProcessor implements ItemProcessor<SalesInfoDto, SalesInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesInfoItemProcessor.class);

    private final SalesInfoMapper salesInfoMapper;

    public SalesInfoItemProcessor(SalesInfoMapper salesInfoMapper) {
        this.salesInfoMapper = salesInfoMapper;
    }

    @Override
    public SalesInfo process(SalesInfoDto salesInfoDto) throws Exception {
        LOGGER.info("processing the Sales: {}", salesInfoDto.toString());
        return salesInfoMapper.toEntity(salesInfoDto);
    }
}