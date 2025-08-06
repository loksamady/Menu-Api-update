package kh.com.csx.service;

import kh.com.csx.dto.CustomerRequest;
import kh.com.csx.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerRequest create(CustomerRequest customerRequest);
    CustomerResponse update(CustomerRequest customerRequest, Long id);
    void delete(Long id);
    List<CustomerResponse> findAll();
    CustomerResponse findById(Long id);
}
