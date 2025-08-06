package kh.com.csx.repository;

import kh.com.csx.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
//    Page<Merchant> findAllByOrderByIdDesc(Pageable pageable);

    List<Merchant> findAllByOrderByIdDesc();

    List<Merchant> findByVendorIdAndActive(Integer vendorId, Integer active);

    List<Merchant> findByVendorId(Integer vendorId);

    Optional<Merchant> findBySlugAndActive(String slug, Integer active);

    Boolean existsByName(String name);

    Boolean existsByPrimaryPhone(String primaryPhone);

    Boolean existsBySlug(String slug);

    Boolean existsByNameAndIdNot(String name, Integer id);

    Boolean existsByPrimaryPhoneAndIdNot(String primaryPhone, Integer id);

    Boolean existsBySlugAndIdNot(String slug, Integer id);
}
