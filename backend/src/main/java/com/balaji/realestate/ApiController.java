package com.balaji.realestate;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"${app.frontend-origin:http://localhost:5173}", "http://127.0.0.1:5173"})
public class ApiController {
    private final PropertyRepository properties;
    private final EnquiryRepository enquiries;
    private final ConcurrentHashMap<String, Instant> sessions = new ConcurrentHashMap<>();
    @Value("${app.admin-password:}") private String adminPassword;
    public ApiController(PropertyRepository properties, EnquiryRepository enquiries) { this.properties=properties; this.enquiries=enquiries; }

    @GetMapping("/properties") public Object listProperties() { return properties.findAllByStatusOrderByFeaturedDescCreatedAtDesc("AVAILABLE"); }
    @GetMapping("/properties/{id}") public Property property(@PathVariable Long id) { return properties.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); }
    @PostMapping("/enquiries") @ResponseStatus(HttpStatus.CREATED) public Enquiry enquiry(@Valid @RequestBody Enquiry enquiry) { return enquiries.save(enquiry); }

    @PostMapping("/admin/login") public Map<String,String> login(@RequestBody Map<String,String> request) {
        if (adminPassword.isBlank() || !adminPassword.equals(request.get("password"))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid admin password");
        String token = UUID.randomUUID().toString();
        sessions.put(token, Instant.now().plusSeconds(8 * 60 * 60));
        return Map.of("token", token);
    }
    @GetMapping("/admin/properties") public Object adminProperties(@RequestHeader(value="X-Admin-Key", required=false) String key) { authorize(key); return properties.findAll(); }
    @PostMapping("/properties") @ResponseStatus(HttpStatus.CREATED) public Property create(@RequestHeader(value="X-Admin-Key", required=false) String key, @Valid @RequestBody Property p) { authorize(key); p.setId(null); return properties.save(p); }
    @PutMapping("/properties/{id}") public Property update(@RequestHeader(value="X-Admin-Key", required=false) String key, @PathVariable Long id, @Valid @RequestBody Property p) { authorize(key); if (!properties.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND); p.setId(id); return properties.save(p); }
    @DeleteMapping("/properties/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@RequestHeader(value="X-Admin-Key", required=false) String key, @PathVariable Long id) { authorize(key); properties.deleteById(id); }
    @GetMapping("/admin/enquiries") public Object adminEnquiries(@RequestHeader(value="X-Admin-Key", required=false) String key) { authorize(key); return enquiries.findAll(); }
    private void authorize(String key) {
        Instant expiry = key == null ? null : sessions.get(key);
        if(expiry == null || expiry.isBefore(Instant.now())) {
            if(key != null) sessions.remove(key);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
