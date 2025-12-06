package Doska.data;

import lombok.Data;

// конструктор пользователя
@Data
public class User {
    private String email;
    private String password;
    private String submitPassword;

}
