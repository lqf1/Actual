package cn.com.taiji.actual.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author zxx
 * @date 2018/12/14 10:52
 * @version 1.0
 */

@Entity
@Table(name = "sys_user")
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 5498564626120974124L;
    @Id
    @GeneratedValue
    private Integer uid;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private Date createDate;

    private String state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roles;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Blog> blogList;
//    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @ManyToMany
    @JoinTable(name = "Discussion_User",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "did")})
    private List<DiscussionGroup> disGroupList;

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createDate=" + createDate +
                ", state='" + state + '\'' +
                '}';
    }
}
