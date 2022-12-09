package fr.gopartner.kafkasalesinfo.core.kafka;

import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfo;
import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SalesItemWriter implements ItemWriter<SalesInfo> {

    private final SalesRepository salesRepository;

    public SalesItemWriter(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public void write(List<? extends SalesInfo> list) throws Exception {
        log.info("The list contains {} elements are saved successfully", list.size());
        list.forEach(salesRepository::save);
    }
}
