package kh.com.csx.repository;

import kh.com.csx.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    Boolean existsByIdAndStatus(Integer id, Integer status);
}
