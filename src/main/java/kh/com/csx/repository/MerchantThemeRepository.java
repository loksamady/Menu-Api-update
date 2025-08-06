package kh.com.csx.repository;

import kh.com.csx.entity.MerchantTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantThemeRepository extends JpaRepository<MerchantTheme, Integer> {
    Optional<MerchantTheme> findByMerchantId(Integer merchantId);
}
