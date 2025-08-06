package kh.com.csx.repository;

import kh.com.csx.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByStatus(Integer status);

    List<Menu> findByMerchantId(Integer merchantId, Sort sort);

    List<Menu> findByMerchantIdAndStatus(Integer merchantId, Integer status, Sort sort);
}
