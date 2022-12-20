package nl.belastingdienst.barordersystem.Controllers;

import nl.belastingdienst.barordersystem.Dto.OrderLineRecieveDto;
import nl.belastingdienst.barordersystem.Dto.OrderLineSendDto;
import nl.belastingdienst.barordersystem.Models.Enums.Status;
import nl.belastingdienst.barordersystem.Models.OrderLine;
import nl.belastingdienst.barordersystem.Services.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderLineController {

    OrderLineService orderLineService;

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }


    @PostMapping(value = "")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderLineSendDto orderDto, BindingResult br) {
        StringBuilder sb = new StringBuilder();
        if (br.hasErrors()) {
            for (FieldError error : br.getFieldErrors()) {
                sb.append(error.getField()).append(": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            OrderLine newOrder = orderLineService.createOrder(orderDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newOrder.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
        orderLineService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/claim")
    public ResponseEntity<String> claimOrder(@PathVariable Long id, @RequestParam("staffId") Long staffId) {

        orderLineService.setStatusOrder(Status.PREPARING, staffId, id);
        return ResponseEntity.ok("You have claimed order number: " + id);
    }

    @PutMapping(value = "/{id}/finish")
    public ResponseEntity<String> finishOrder(@PathVariable Long id, @RequestParam("staffId") Long staffId) {
        orderLineService.setStatusOrder(Status.DONE, staffId, id);
        return ResponseEntity.ok("You have finished order number: " + id);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<OrderLineRecieveDto>> getAllOrders() {
        List<OrderLineRecieveDto> orders = orderLineService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping(value = "/{id}/status")
    public ResponseEntity<List<OrderLineRecieveDto>> getStatusOrders(@PathVariable Long id) {
        List<OrderLineRecieveDto> orders = orderLineService.getAllOrdersByCustomer(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(value = "/openorders")
    public ResponseEntity<List<OrderLineRecieveDto>> getAllOpenOrders() {
        List<OrderLineRecieveDto> orders = orderLineService.getAllOpenOrders();
        return ResponseEntity.ok(orders);
    }


}