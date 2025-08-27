package pro.arcodeh.msgoba_2002_server.protoProfile;

import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.models.ProtoProfile;
import pro.arcodeh.msgoba_2002_server.repositories.ProtoProfileRepository;

@Component
public class ProtoProfileService {

    private final ProtoProfileRepository protoProfileRepository;

    public ProtoProfileService(ProtoProfileRepository protoProfileRepository) {
        this.protoProfileRepository = protoProfileRepository;
    }

    public ProtoProfile getProtoProfileByEmail(String email) {
        return this.protoProfileRepository.findByEmailIgnoreCase(email);
    }
}
