package pro.arcodeh.msgoba_2002_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.arcodeh.msgoba_2002_server.models.ProtoProfile;

import java.util.UUID;

public interface ProtoProfileRepository extends JpaRepository<ProtoProfile, UUID> {
    ProtoProfile findByEmailIgnoreCase(String email);
}
