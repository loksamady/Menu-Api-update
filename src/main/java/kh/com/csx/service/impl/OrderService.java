package kh.com.csx.service.impl;

    import kh.com.csx.dto.OrderMenuRequest;
    import kh.com.csx.entity.Customer;
    import kh.com.csx.entity.Menu;
    import kh.com.csx.entity.Order;
    import kh.com.csx.entity.OrderMenu;
    import kh.com.csx.repository.CustomerRepository;
    import kh.com.csx.repository.MenuRepository;
    import kh.com.csx.repository.OrderRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Service
    @Transactional
    @RequiredArgsConstructor
    public class OrderService {
        private final CustomerRepository customerRepository;
        private final MenuRepository menuRepository;
        private final OrderRepository orderRepository;

        public Order placeOrder(Long customerId, List<OrderMenuRequest> orderMenuRequestList) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            Order order = new Order();
            order.setCustomer(customer);
            order.setCustomerName(customer.getUsername());
//            order.setCustomerAddress(customer.getAddress());
            order.setCustomerPhone(customer.getPhoneNumber());
            order.setCreatedAt(new Date());

            BigDecimal totalPrice = BigDecimal.ZERO;
            Set<OrderMenu> orderMenuSet = new HashSet<>();
            for (OrderMenuRequest orderMenuRequest : orderMenuRequestList) {
                Menu menu = menuRepository.findById(Math.toIntExact(orderMenuRequest.getMenuId()))
                        .orElseThrow(() -> new RuntimeException("Menu not found"));
                OrderMenu orderMenu = new OrderMenu();
                orderMenu.setOrder(order);
                orderMenu.setMenu(menu);
                orderMenu.setMenuName(menu.getNameEn());
                orderMenu.setQuantity(orderMenuRequest.getQuantity().intValue()); // Convert BigDecimal to int
                orderMenu.setPrice(BigDecimal.valueOf(menu.getPrice())); // Convert double to BigDecimal
                orderMenu.setDiscount(BigDecimal.valueOf(menu.getDiscount())); // Assuming Menu has a discount field

                BigDecimal totalMenuPrice = orderMenu.getPrice()
                        .multiply(BigDecimal.valueOf(orderMenu.getQuantity()))
                        .subtract(orderMenu.getDiscount());
                orderMenu.setTotalPrice(BigDecimal.valueOf(totalMenuPrice.doubleValue())); // Set total price for the menu item
                totalPrice = totalPrice.add(totalMenuPrice); // Accumulate total price

                // Add to Set to avoid duplicates

                orderMenuSet.add(orderMenu);
            }
            order.setOrderMenus(new ArrayList<>(orderMenuSet)); // Convert Set to List
            order.setTotalPrice(totalPrice); // Ensure Order entity has this method

            return orderRepository.save(order);
        }
    }