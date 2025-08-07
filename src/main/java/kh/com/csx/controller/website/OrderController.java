package kh.com.csx.controller.website;

import jakarta.validation.Valid;
import kh.com.csx.dto.OrderDetailResponse;
import kh.com.csx.dto.OrderMenuResponse;
import kh.com.csx.dto.OrderRequest;
import kh.com.csx.entity.Order;
import kh.com.csx.repository.OrderRepository;
import kh.com.csx.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class OrderController {
    public final OrderRepository orderRepository;
    public final OrderService orderService;

    @PostMapping("/api/v1/website/order")
    public OrderDetailResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest.getCustomerId(),
                orderRequest.getOrderMenuRequests());
        return mapToResponse(order);
    }

    @GetMapping("/api/v1/website/order/{id}")
    public OrderDetailResponse getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    private OrderDetailResponse mapToResponse(Order order) {
        OrderDetailResponse dto = new OrderDetailResponse();
        dto.setOrderId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getUsername());
        dto.setCustomerPhone(order.getCustomer().getPhoneNumber());
        dto.setCustomerAddress(order.getCustomer().getAddress());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreated_at());
        dto.setOrderMenuRequests(order.getOrderMenus().stream().map(orderMenu -> {
            OrderMenuResponse im = new OrderMenuResponse();
            im.setMenuId(orderMenu.getMenu().getMenuId());
            im.setMenuName(orderMenu.getMenu().getNameKh());
            im.setQuantity(orderMenu.getQuantity());
            im.setOrderId(orderMenu.getId());
            im.setPriceAtOrder(orderMenu.getPriceAtOrder());
            return im;
        }).toList());
        return dto;
    }
}