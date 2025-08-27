package pro.arcodeh.msgoba_2002_server.protoProfile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pro.arcodeh.msgoba_2002_server.models.ProtoProfile;

@RestController
@RequestMapping("/protoprofile")
public class ProtoProfileController {
    private final ProtoProfileService protoProfileService;

    public ProtoProfileController(ProtoProfileService protoProfileService) {
        this.protoProfileService = protoProfileService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("")
    public ResponseEntity<?> getProtoProfile(@RequestParam String email) {
        System.out.println("Supplied email: " + email);
        ProtoProfile protoProfile = this.protoProfileService.getProtoProfileByEmail(email);

        if(protoProfile != null) {
            return ResponseEntity.ok(protoProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
