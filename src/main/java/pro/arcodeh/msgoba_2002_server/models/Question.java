package pro.arcodeh.msgoba_2002_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Question extends BaseEntity{

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private List<String> options = new ArrayList<>();

    @Column(nullable = false)
    private Integer correctOption;

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getOption(Integer index) {
        return this.options.get(index);
    }
}
