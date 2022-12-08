package fr.gopartner.kafkasalesinfo.domain.salesInfo;

import org.springframework.stereotype.Service;

@Service
public class SalesInfoMapper {

    public SalesInfo toEntity(SalesInfoDto salesInfoDto) {
        if (salesInfoDto == null) {
            return null;
        }
        return SalesInfo.builder()
                .product(salesInfoDto.getProduct())
                .seller(salesInfoDto.getSeller())
                .sellerId(salesInfoDto.getSellerId())
                .price(salesInfoDto.getPrice())
                .city(salesInfoDto.getCity())
                .category(salesInfoDto.getCategory())
                .build();
    }

}
