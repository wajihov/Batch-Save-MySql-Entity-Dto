package fr.gopartner.kafkasalesinfo.core.kafka;

import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesInfo;
import fr.gopartner.kafkasalesinfo.domain.salesInfo.SalesRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesItemWriter implements ItemWriter<SalesInfo> {

    private final SalesRepository salesRepository;

    public SalesItemWriter(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public void write(List<? extends SalesInfo> list) throws Exception {
        list.forEach(salesRepository::save);
    }
}
