package pro.arcodeh.msgoba_2002_server.admin;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/superadmin")
public class AdminController {

    @PostMapping("/users/{uid}/claims")
    @PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).SUPERADMIN.name())")
    public Map<String, Object> setCustomClaims(@PathVariable String uid, @RequestBody Map<String, Object> claims) {
        try {
            FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
            return Map.of("message", "Custom claims set successfully for user: " + uid);
        } catch(FirebaseAuthException e) {
            throw new RuntimeException("Error setting custom claims: " + e.getMessage(), e);
        }
    }
}
