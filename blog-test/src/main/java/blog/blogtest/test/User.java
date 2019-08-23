package blog.blogtest.test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @NotEmpty(message = "用户名不能为空")
    @Length(min = 5, message = "用户名长度不能小于5位")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位")
    private String password;
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    /**
     * 校验是否合法用户
     *
     * @param user
     * @return
     */
    public static boolean isUserValid(User user) {
        return "admin".equals(user.getUsername()) && "123456".equals(user.getPassword());
    }
}
