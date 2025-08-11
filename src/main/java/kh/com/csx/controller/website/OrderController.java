package kh.com.csx.controller.website;

import jakarta.validation.Valid;
import kh.com.csx.constant.Constant;
import kh.com.csx.dto.OrderDetailResponse;
import kh.com.csx.dto.OrderMenuResponse;
import kh.com.csx.dto.OrderRequest;
import kh.com.csx.entity.Order;
import kh.com.csx.repository.OrderRepository;
import kh.com.csx.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.API_V1_WEBSITE + "/order")
public class OrderController {
    public final OrderRepository orderRepository;
    public final OrderService orderService;

    @PostMapping
    public OrderDetailResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest.getCustomerId(),
                orderRequest.getOrderMenuRequests());
        return mapToResponse(order);
    }

    @GetMapping("order")
    public List<OrderDetailResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAllWithOrderMenus();
        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public OrderDetailResponse getOrderById(@PathVariable("id") Long orderId) {
        Optional<Order> orderOptional = orderRepository.findByIdWithOrderMenus(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        Order order = orderOptional.get();
        return mapToResponse(order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable("id") Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        Order order = orderOptional.get();
        orderRepository.delete(order);
    }
    private OrderDetailResponse mapToResponse(Order order) {
        OrderDetailResponse dto = new OrderDetailResponse();
        dto.setOrderId(order.getId());
        dto.setCustomerId(order.getCustomer().getId()); // Correctly set customer ID
        dto.setCustomerName(order.getCustomer().getUsername());
        dto.setCustomerPhone(order.getCustomer().getPhoneNumber());
        dto.setCustomerAddress(order.getCustomer().getAddress());
        dto.setTotal_amount(order.getTotalPrice());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setOrderMenuRequests(order.getOrderMenus().stream().map(orderMenu -> {
            OrderMenuResponse im = new OrderMenuResponse();
            im.setMenuId(orderMenu.getMenu().getMenuId());
            im.setMenuName(orderMenu.getMenu().getNameKh());
            im.setPrice(orderMenu.getPrice());
            im.setDiscount(orderMenu.getDiscount());
            im.setQuantity(BigDecimal.valueOf(orderMenu.getQuantity()));
            im.setTotalPrice(orderMenu.getTotalPrice());
            return im;
        }).toList());
        return dto;
    }
}
