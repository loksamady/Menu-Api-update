package kh.com.csx.service.admin;

import kh.com.csx.dto.SubscriptionTypeDTO;
import kh.com.csx.entity.SubscriptionType;
import kh.com.csx.exception.APIException;
import kh.com.csx.repository.SubscriptionTypeRepository;
import kh.com.csx.request.subscriptionType.SubscriptionTypeFilterRequest;
import kh.com.csx.request.subscriptionType.SubscriptionTypeRequest;
import kh.com.csx.request.subscriptionType.SubscriptionTypeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionTypeService.class);
    private final SubscriptionTypeRepository repository;

    public SubscriptionTypeService(SubscriptionTypeRepository repository) {
        this.repository = repository;
    }

    public Page<SubscriptionTypeDTO> getAll(SubscriptionTypeFilterRequest filter, Pageable pageable) {
        Specification<SubscriptionType> spec = SubscriptionTypeSpecification.filterBy(filter);
        Page<SubscriptionType> subscriptionTypes = repository.findAll(spec, pageable);

        LOGGER.info("Get All Subscription Type: {}", subscriptionTypes);

        return subscriptionTypes.map(SubscriptionTypeDTO::toListDTO);
    }

    @Transactional
    public List<SubscriptionTypeDTO> getOption() {
        List<SubscriptionTypeDTO> subscriptionTypes = repository.findAll()
                .stream()
                .map(SubscriptionTypeDTO::toListDTO)
                .toList();

        LOGGER.info("Get Subscription Type Options: {}", subscriptionTypes);

        return subscriptionTypes;
    }

    @Transactional
    public SubscriptionTypeDTO getOne(Integer id) {
        SubscriptionTypeDTO subscriptionType = repository.findById(id)
                .map(SubscriptionTypeDTO::toListDTO)
                .orElseThrow(APIException::notFound);

        LOGGER.info("Get Subscription Type: {}", subscriptionType);

        return subscriptionType;
    }

    @Transactional
    public SubscriptionType create(SubscriptionTypeRequest request) {
        SubscriptionType subscriptionType = new SubscriptionType();
        SubscriptionType savedSubscriptionType = repository.save(getSubscriptionType(subscriptionType, request));

        LOGGER.info("Successfully Create Subscription Type: {}", savedSubscriptionType);

        return savedSubscriptionType;
    }

    @Transactional
    public SubscriptionType update(Integer id, SubscriptionTypeRequest request) {
        SubscriptionType subscriptionType = repository.findById(id).orElseThrow(APIException::notFound);
        SubscriptionType updatedSubscriptionType = repository.save(getSubscriptionType(subscriptionType, request));

        LOGGER.info("Successfully Update Subscription Type: {}", updatedSubscriptionType);

        return updatedSubscriptionType;
    }

    @Transactional
    public Boolean delete(Integer id) {
        SubscriptionType subscriptionType = repository.findById(id).orElseThrow(APIException::notFound);
        subscriptionType.setDeletedAt(new Date());
        repository.save(subscriptionType);

        LOGGER.info("Successfully Delete Subscription Type ID: {}", id);

        return !repository.existsById(id);
    }

    private SubscriptionType getSubscriptionType(SubscriptionType data, SubscriptionTypeRequest request) {
        if (request.getName() != null) data.setName(request.getName());
        if (request.getPrice() != null) data.setPrice(request.getPrice());
        if (request.getPeriod() != null) data.setPeriod(request.getPeriod());
        if (request.getPeriodUnit() != null) data.setPeriodUnit(request.getPeriodUnit());

        return data;
    }
}
