package kh.com.csx.repository;

import kh.com.csx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByMerchantIdIn(List<Integer> merchantIds);

    List<User> findByMerchantId(Integer merchantId);

    List<User> findByVendorId(Integer vendorId);
}
