package fr.gopartner.kafkasalesinfo.domain.salesInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<SalesInfo, Long> {
}
