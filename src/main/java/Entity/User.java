package Entity;


import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import org.antlr.v4.runtime.misc.NotNull;
import org.aspectj.bridge.Message;
import org.hibernate.annotations.NotFound;

@Entity
@Table(name="Users")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be Empty")
    private String userName;
    @NotBlank(message = "Email Cannot be Empty")

}
