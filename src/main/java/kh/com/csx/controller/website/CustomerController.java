package kh.com.csx.controller.website;

import kh.com.csx.dto.CustomerRequest;
import kh.com.csx.dto.CustomerResponse;
import kh.com.csx.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("api/v1/website/customers")
    public ResponseEntity<Object> create(@RequestBody CustomerRequest customerRequest){
        log.info("Creating customer with request: {}", customerRequest);
        customerService.create(customerRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("api/v1/website/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerRequest customerRequest, @PathVariable String id) {
        log.info("Updating customer with id: {} and request: {}", id, customerRequest);
        customerService.update(customerRequest, Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/v1/website/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {
        log.info("Deleting customer with id: {}", id);
        customerService.delete(Long.parseLong(id));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("api/v1/website/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) {
        log.info("Fetching customer with id: {}", id);
        CustomerResponse customerResponse = customerService.findById(Long.parseLong(id));
        if (customerResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerResponse);
    }
    @GetMapping("api/v1/website/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("Fetching all customers");
        List<CustomerResponse> customerResponses = customerService.findAll();
        return ResponseEntity.ok(customerResponses);
    }
}
