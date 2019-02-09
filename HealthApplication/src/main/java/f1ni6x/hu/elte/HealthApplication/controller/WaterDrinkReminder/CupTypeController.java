package f1ni6x.hu.elte.HealthApplication.controller.WaterDrinkReminder;

import f1ni6x.hu.elte.HealthApplication.model.WaterDrinkReminder.CupType;
import f1ni6x.hu.elte.HealthApplication.repository.UserRepository;
import f1ni6x.hu.elte.HealthApplication.repository.WaterDrinkReminder.CupTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cuptypes")
// @Secured({ "ROLE_USER", "ROLE_ADMIN"})
public class CupTypeController {

    @Autowired
    private CupTypeRepository cupTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public Iterable<CupType> getAll() {
        return cupTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CupType> getById(@PathVariable Integer id) {
        Optional<CupType> optionalRequest = cupTypeRepository.findById(id);
        if (optionalRequest.isPresent()) {
            return ResponseEntity.ok(optionalRequest.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CupType> create(@RequestBody CupType request) {
        if (request.getCupID() != null && cupTypeRepository.existsById(request.getCupID())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(cupTypeRepository.save(request));
    }
}
