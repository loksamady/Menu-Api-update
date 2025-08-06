package kh.com.csx.repository;

import kh.com.csx.entity.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Integer>, JpaSpecificationExecutor<SubscriptionType> {
}
