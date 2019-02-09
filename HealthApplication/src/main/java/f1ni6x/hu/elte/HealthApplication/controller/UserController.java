package f1ni6x.hu.elte.HealthApplication.controller;

import f1ni6x.hu.elte.HealthApplication.model.User;
import f1ni6x.hu.elte.HealthApplication.model.WaterDrinkReminder.CupType;
import f1ni6x.hu.elte.HealthApplication.repository.UserRepository;
import f1ni6x.hu.elte.HealthApplication.repository.WaterDrinkReminder.CupTypeRepository;
import f1ni6x.hu.elte.HealthApplication.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Secured({ "ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CupTypeRepository cupTypeRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @GetMapping("")
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<User> login() {
        return ResponseEntity.ok(authenticatedUser.getUser());
    }

    @GetMapping("/{id}/cuptypes")
    public ResponseEntity<List<CupType>> getUserCupTypes(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get().getMyCups());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("")
    public ResponseEntity<User> create(@RequestBody User user) {
        Optional<User> optionalUser = userRepository.findByUserName(user.getUserName());
        if (optionalUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.ROLE_USER);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/{id}/cuptypes")
    public ResponseEntity<List<CupType>> addCupType(@PathVariable Integer id, @RequestBody CupType request) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<CupType> optionalRequest = cupTypeRepository.findById(request.getCupID());
        if (optionalUser.isPresent() && optionalRequest.isPresent()) {
            User user = optionalUser.get();
            user.getMyCups().add(optionalRequest.get());
            userRepository.save(user);
            return ResponseEntity.ok(user.getMyCups());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/cupTypes/{cupId}")
    public ResponseEntity removeRequest(@PathVariable Integer userId,
                                        @PathVariable Integer cupId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<CupType> optionalRequest = user.getMyCups().stream()
                    .filter(cupType -> cupType.getCupID() == cupId)
                    .findFirst();
            if (optionalRequest.isPresent()) {
                List<CupType> filteredRequests = user.getMyCups().stream()
                        .filter(cupType -> cupType.getCupID() != cupId)
                        .collect(Collectors.toList());
                user.setMyCups(filteredRequests);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}