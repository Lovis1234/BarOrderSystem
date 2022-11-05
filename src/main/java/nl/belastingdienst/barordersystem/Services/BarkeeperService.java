package nl.belastingdienst.barordersystem.Services;

import nl.belastingdienst.barordersystem.Dto.BarkeeperDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Barkeeper;
import nl.belastingdienst.barordersystem.Repositories.BarkeeperRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BarkeeperService {
    BarkeeperRepository barkeeperRepository;

    public BarkeeperService(BarkeeperRepository barkeeperRepository) {
        this.barkeeperRepository = barkeeperRepository;
    }

    public List<BarkeeperDto> getAllBarkeepers() {
        List<Barkeeper> barkeepers = barkeeperRepository.findAll();
        List<BarkeeperDto> barkeeperDtos = new ArrayList<>();
        for(Barkeeper barkeeper : barkeepers){
            barkeeperDtos.add(fromBarkeeper(barkeeper));
        }
        return barkeeperDtos;
    }
    public BarkeeperDto createBarkeeper(BarkeeperDto barkeeperDto) {
        Barkeeper barkeeper = toBarkeeper(barkeeperDto);
        Barkeeper newBarkeeper = barkeeperRepository.save(barkeeper);
        BarkeeperDto dto = fromBarkeeper(newBarkeeper);
        return dto;

    }


    private BarkeeperDto fromBarkeeper(Barkeeper barkeeper){
        BarkeeperDto barkeeperDto = new BarkeeperDto();
        barkeeperDto.setId(barkeeper.getId());
        barkeeperDto.setName(barkeeper.getName());
        return barkeeperDto;
    }

    private Barkeeper toBarkeeper(BarkeeperDto barkeeperDto){
        Barkeeper barkeeper = new Barkeeper();
        barkeeper.setId(barkeeperDto.getId());
        barkeeper.setName(barkeeperDto.getName());
        return barkeeper;
    }

    public void deleteBarkeeper(Long id) {
        Optional<Barkeeper> barkeeperOptional = barkeeperRepository.findById(id);
        if (barkeeperOptional.isPresent()) {
            barkeeperRepository.delete(barkeeperOptional.get());
        } else throw new RecordNotFoundException("Barkeeper not found");
    }
}

