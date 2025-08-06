//package kh.com.csx.controller.website;
//
//import jakarta.validation.Valid;
//import kh.com.csx.dto.OrderDetailResponse;
//import kh.com.csx.dto.OrderRequest;
//import kh.com.csx.entity.Order;
//import kh.com.csx.repository.OrderRepository;
//import kh.com.csx.service.impl.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//public class OrderController {
//    public final OrderRepository orderRepository;
//    public final OrderService orderService;
//    @PostMapping("api/order")
//    public OrderDetailResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
//        Order order = orderService.placeOrder(orderRequest.getCustomerId(),
//                orderRequest.getOrderMenuRequests());
//        return mapToResponse(order);
//    }
//    @GetMapping("api/order/{id}")
//    public OrderDetailResponse getOrderById(@PathVariable Long id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
//        return mapToResponse(order);
//    }
//}
