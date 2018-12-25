package cn.com.taiji.actual.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zxx
 * @date 2018/12/14 15:56
 * @version 1.0
 */

@Entity
@Table(name = "sys_role")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 5709199711777078687L;
    @Id
    @GeneratedValue
    private Integer rid;

    private String roleName;

    private Date createDate;

    private String state;

    @ManyToMany
    @JoinTable(name = "RolePermission",
            joinColumns = {@JoinColumn(name = "rid")},inverseJoinColumns = {@JoinColumn(name = "pid")})
    private List<Permission> permissions;
}
