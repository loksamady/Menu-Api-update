package kh.com.csx.repository;

import kh.com.csx.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Modifying
    @Query(value = "DELETE FROM menu_categories WHERE category_id = :categoryId", nativeQuery = true)
    void removeCategoryFromAllMenus(@Param("categoryId") Integer categoryId);

    List<Category> findByStatus(Integer status, Sort sort);

    List<Category> findByMerchantId(Integer merchantId, Sort sort);

    List<Category> findByMerchantIdAndStatus(Integer merchantId, Integer status, Sort sort);

    List<Category> findByMerchantIdInAndStatus(List<Integer> merchantIds, Integer status, Sort sort);
}