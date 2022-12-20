package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.OrderLineRecieveDto;
import nl.belastingdienst.barordersystem.Dto.OrderLineSendDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Drink;
import nl.belastingdienst.barordersystem.Models.Enums.Status;
import nl.belastingdienst.barordersystem.Models.OrderLine;
import nl.belastingdienst.barordersystem.Repositories.BarkeeperRepository;
import nl.belastingdienst.barordersystem.Repositories.CustomerRepository;
import nl.belastingdienst.barordersystem.Repositories.DrinkRepository;
import nl.belastingdienst.barordersystem.Repositories.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {
    OrderLineRepository orderRepository;

    DrinkRepository drinkRepository;

    CustomerRepository customerRepository;

    BarkeeperRepository barkeeperRepository;

    DrinkService drinkService;


    public OrderLineService(OrderLineRepository orderRepository, DrinkRepository drinkRepository, CustomerRepository customerRepository, BarkeeperRepository barkeeperRepository, DrinkService drinkService) {
        this.orderRepository = orderRepository;
        this.drinkRepository = drinkRepository;
        this.customerRepository = customerRepository;
        this.barkeeperRepository = barkeeperRepository;
        this.drinkService = drinkService;
    }

    public List<OrderLineRecieveDto> getAllOrders() {
        List<OrderLine> orders = orderRepository.findAll();
        List<OrderLineRecieveDto> orderLineRecieveDtos = new ArrayList<>();
        for (OrderLine order : orders) {
            orderLineRecieveDtos.add(fromOrderToOrderRecieveDto(order));
        }
        return orderLineRecieveDtos;
    }

    public List<OrderLineRecieveDto> getAllOrdersByCustomer(Long id) {
        List<OrderLine> orders = orderRepository.findAllByCustomer_Id(id);
        List<OrderLineRecieveDto> orderLineRecieveDtos = new ArrayList<>();
        for (OrderLine order : orders) {
            orderLineRecieveDtos.add(fromOrderToOrderRecieveDto(order));
        }
        return orderLineRecieveDtos;
    }

    public List<OrderLineRecieveDto> getAllOpenOrders() {
        List<OrderLine> orders = orderRepository.findAllByStatusNotLike(Status.DONE);
        List<OrderLineRecieveDto> orderLineRecieveDtos = new ArrayList<>();
        for (OrderLine order : orders) {
            orderLineRecieveDtos.add(fromOrderToOrderRecieveDto(order));
        }
        return orderLineRecieveDtos;
    }

    public OrderLine getOrderById(Long id) {
        Optional<OrderLine> OrderOptional = orderRepository.findById(id);
        if (OrderOptional.isEmpty()) {
            throw new RecordNotFoundException("OrderLine not found");
        } else {
            return OrderOptional.get();
        }
    }

    public OrderLine createOrder(OrderLineSendDto orderLineSendDto) {
        OrderLine order = toOrder(orderLineSendDto);
        orderRepository.save(order);
        return order;

    }

    private void claimOrder(Long staffId, Long orderLineId) {
        if (barkeeperRepository.findById(staffId).isPresent()) {
            OrderLine orderLine = getOrderById(orderLineId);
            orderLine.setBarkeeper(barkeeperRepository.findById(staffId).get());
            orderRepository.save(orderLine);
        } else throw new RecordNotFoundException("Barkeeper not found");
    }

    public void setStatusOrder(Status status, Long staffId, Long orderId) {
        OrderLine orderLine = getOrderById(orderId);
        orderLine.setStatus(status);
        orderRepository.save(orderLine);
        if (status == Status.PREPARING) {
            claimOrder(staffId, orderId);
        }
    }


    private OrderLineRecieveDto fromOrderToOrderRecieveDto(OrderLine order) {
        OrderLineRecieveDto orderLineRecieveDto = new OrderLineRecieveDto();
        orderLineRecieveDto.setId(order.getId());
        orderLineRecieveDto.setDrinkList(drinkService.fromDrinkList(order.getDrinkList()));
        orderLineRecieveDto.setStatus(order.getStatus());
        orderLineRecieveDto.setBarkeeper(order.getBarkeeper());
        double price = 0;
        for (Drink drink : order.getDrinkList()) {
            price += drink.getPrice();
        }
        orderLineRecieveDto.setPrice(price);
        return orderLineRecieveDto;
    }

    private OrderLine toOrder(OrderLineSendDto orderLineSendDto) {
        OrderLine order = new OrderLine();
        order.setId(orderLineSendDto.getId());
        if (customerRepository.findById(orderLineSendDto.getCustomer()).isPresent()) {
            order.setCustomer(customerRepository.findById(orderLineSendDto.getCustomer()).get());
        } else throw new RecordNotFoundException("Customer not found");
        Long[] drinkIds = orderLineSendDto.getDrinkList();
        List<Drink> list = new ArrayList<>();
        for (Long drinkId : drinkIds) {
            if (drinkRepository.findById(drinkId).isPresent()) {
                list.add(drinkRepository.findById(drinkId).get());
            } else throw new RecordNotFoundException("Drink not found");
        }
        order.setDrinkList(list);
        double price = 0;
        for (Drink drink : order.getDrinkList()) {
            price += drink.getPrice();
        }
        order.setPrice(price);
        order.setStatus(Status.RECIEVED);
        return order;

    }

    public void deleteOrder(Long id) {
        Optional<OrderLine> orderLineOptional = orderRepository.findById(id);
        if (orderLineOptional.isPresent()) {
            orderRepository.delete(orderLineOptional.get());
        } else throw new RecordNotFoundException("Order not found");
    }
}

