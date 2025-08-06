package kh.com.csx.repository;

import kh.com.csx.entity.Page;
import kh.com.csx.projection.PageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {

    @Query("SELECT p.id AS id, p.parentId AS parentId, p.titleEn AS titleEn, p.titleKh AS titleKh, p.url AS url, p.path AS path, p.pageComponent AS pageComponent, p.displayOrder AS displayOrder, p.status AS status, p.parameter AS parameter " +
            "FROM Page p " +
            "WHERE p.parentId IS NULL AND p.status != 2 " +
            "ORDER BY COALESCE(p.displayOrder, 99999) ASC")
    List<PageProjection> adminFindTopLevelPages();

    @Query("SELECT p.id AS id, p.parentId AS parentId, p.titleEn AS titleEn, p.titleKh AS titleKh, p.url AS url, p.path AS path, p.pageComponent AS pageComponent, p.displayOrder AS displayOrder, p.status AS status, p.parameter AS parameter " +
            "FROM Page p " +
            "WHERE p.parentId = :parentId AND p.status IN :statuses " +
            "ORDER BY COALESCE(p.displayOrder, 99999) ASC")
    List<PageProjection> findChildPages(@Param("parentId") Integer parentId, @Param("statuses") List<Integer> statuses);

    @Query("SELECT p.id AS id, p.parentId AS parentId, p.titleEn AS titleEn, p.titleKh AS titleKh, p.url AS url, p.path AS path, p.pageComponent AS pageComponent, p.displayOrder AS displayOrder, p.status AS status, p.parameter AS parameter " +
            "FROM Page p " +
            "WHERE p.parentId IS NULL AND p.status = 1 " +
            "ORDER BY COALESCE(p.displayOrder, 99999) ASC")
    List<PageProjection> findTopLevelPages();
}
