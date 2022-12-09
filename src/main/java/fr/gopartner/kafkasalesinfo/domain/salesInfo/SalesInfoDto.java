package fr.gopartner.kafkasalesinfo.domain.salesInfo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SalesInfoDto {

    private String product;
    private String seller;
    private Integer sellerId;
    private double price;
    private String city;
    private String category;


}