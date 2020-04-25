package application.com.cn.orm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//数据库自动递增
    @Column(nullable = false)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    @Email
    private String email;

    public User(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
