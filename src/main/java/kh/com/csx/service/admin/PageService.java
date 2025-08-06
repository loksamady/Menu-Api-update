package kh.com.csx.service.admin;

import kh.com.csx.dto.PageDTO;
import kh.com.csx.request.PageRequest;
import kh.com.csx.entity.Page;
import kh.com.csx.exception.APIException;
import kh.com.csx.projection.PageProjection;
import kh.com.csx.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page createPage(PageRequest pageRequest) {
        LOGGER.info("Start creating page with data: {}", pageRequest);

        Page page = new Page();
        createUpdatePage(pageRequest, page);
        page.setCreatedAt(new Date());
        page = pageRepository.save(page);

        LOGGER.info("Successfully created page: {}", pageRequest);

        return page;
    }


    public Page updatePage(Integer id, PageRequest pageRequest) {
        LOGGER.info("Start updating page with id and page request : {} {}", id, pageRequest);
        Page page = getPage(id);

        createUpdatePage(pageRequest, page);
        page.setUpdatedAt(new Date());
        page = pageRepository.save(page);

        LOGGER.info("Successfully updated page: {}", page);

        return page;
    }

    private void createUpdatePage(PageRequest pageRequest, Page page) {
        Page parentPage = null;
        if (pageRequest.getParentId() != null) {
            LOGGER.info("Get parent page: {}", parentPage);
            parentPage = this.getPage(Integer.valueOf(pageRequest.getParentId()));
            page.setParentId(parentPage.getId());
        } else {
            page.setParentId(null);
        }
        page.setTitleEn(pageRequest.getTitleEn());
        page.setTitleKh(pageRequest.getTitleKh());
        page.setUrl(pageRequest.getUrl());
        if (pageRequest.getDisplayOrder() != null) {
            page.setDisplayOrder(Integer.valueOf(pageRequest.getDisplayOrder()));
        }
        if (pageRequest.getStatus() != null) {
            page.setStatus(Integer.valueOf(pageRequest.getStatus()));
        }
        if (page.getParentId() != null) {
            assert parentPage != null;
            String path = pageRequest.getTitleEn().toLowerCase()
                    .replaceAll("[^a-z0-9]", "-")   // Replace non-alphanumeric characters with hyphens
                    .replaceAll("-{2,}", "-");      // Replace multiple consecutive hyphens with a single hyphen
            page.setPath(parentPage.getPath() + "/" + path);
        } else {
            String path = pageRequest.getTitleEn().toLowerCase()
                    .replaceAll("[^a-z0-9]", "-")   // Replace non-alphanumeric characters with hyphens
                    .replaceAll("-{2,}", "-");      // Replace multiple consecutive hyphens with a single hyphen
            page.setPath("/" + path);
        }


        page.setDescriptionEn(pageRequest.getDescriptionEn());
        page.setDescriptionKh(pageRequest.getDescriptionKh());
    }


    public List<PageDTO> adminGetPages() {
        List<PageProjection> topLevelPages = pageRepository.adminFindTopLevelPages();
        List<Integer> statuses = Arrays.asList(0, 1);
        return mapPagesToDto(topLevelPages, statuses);
    }

    public List<PageDTO> mapPagesToDto(List<PageProjection> pages, List<Integer> statuses) {
        return pages.stream().map(pageProjection -> {
            PageDTO pageDTO = new PageDTO();
            pageDTO.setId(pageProjection.getId());
            pageDTO.setParentId(pageProjection.getParentId());
            pageDTO.setTitleEn(pageProjection.getTitleEn());
            pageDTO.setTitleKh(pageProjection.getTitleKh());
            pageDTO.setUrl(pageProjection.getUrl());
            pageDTO.setPath(pageProjection.getPath());
            pageDTO.setPageComponent(pageProjection.getPageComponent());
            pageDTO.setDisplayOrder(pageProjection.getDisplayOrder());
            pageDTO.setStatus(pageProjection.getStatus());
            pageDTO.setParameter(pageProjection.getParameter());
            List<PageDTO> childPagesDTO = mapPagesToDto(pageRepository.findChildPages(pageProjection.getId(), statuses), statuses);
            pageDTO.setChildPages(childPagesDTO);
            return pageDTO;
        }).toList();
    }

    public List<PageDTO> getPages() {
        List<PageProjection> topLevelPages = pageRepository.findTopLevelPages();
        LOGGER.info("pages: {}", topLevelPages);
        List<Integer> statuses = Arrays.asList(1);
        return mapPagesToDto(topLevelPages,statuses);
    }

    public Page getPage(Integer id) {
        Page page = pageRepository.findById(id).orElse(null);
        if (page == null) throw new APIException("Could not find page", HttpStatus.NOT_FOUND.value());
        LOGGER.info("Successfully get page: {}", page);
        return page;
    }

    public void deletePage(Integer id) {
        Page page = this.getPage(id);
        page.setStatus(2);
        this.pageRepository.save(page);
        LOGGER.info("Successfully delete page with id: {}", id);
    }
}
