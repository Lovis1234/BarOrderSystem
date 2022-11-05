package nl.belastingdienst.barordersystem.Controllers;

import nl.belastingdienst.barordersystem.Dto.BarkeeperDto;
import nl.belastingdienst.barordersystem.Dto.UserDto;
import nl.belastingdienst.barordersystem.Services.BarkeeperService;
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
@RequestMapping(value = "/barkeeper")
public class BarkeeperController {
    BarkeeperService barkeeperService;

    public BarkeeperController(BarkeeperService barkeeperService){
        this.barkeeperService = barkeeperService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<BarkeeperDto>> getAllBarkeepers(){
        List<BarkeeperDto> barkeeperDtos = barkeeperService.getAllBarkeepers();
        return ResponseEntity.ok(barkeeperDtos);
    }



    @PostMapping(value = "/create")
    public ResponseEntity<Object> createBarkeeper(@Valid @RequestBody BarkeeperDto barkeeperDto, BindingResult br){
        StringBuilder sb = new StringBuilder();
        if(br.hasErrors()){
            for(FieldError error : br.getFieldErrors()){
                sb.append(error.getField() + ": ");
                sb.append(error.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {
            BarkeeperDto newBarkeeperDto = barkeeperService.createBarkeeper(barkeeperDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newBarkeeperDto.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBarkeeper(@PathVariable("id") Long id) {
        barkeeperService.deleteBarkeeper(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateBarkeeper(@PathVariable("id") Long id, @RequestBody BarkeeperDto dto) {

        barkeeperService.updateBarkeeper(id, dto);

        return ResponseEntity.noContent().build();
    }
}
