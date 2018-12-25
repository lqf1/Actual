package cn.com.taiji.actual.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zxx
 * @date 2018/12/14 16:01
 * @version 1.0
 */

@Entity
@Table(name = "sys_permission")
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 6298109619272559040L;
    @Id
    @GeneratedValue
    private Integer pid;

    private String permissionName;

    private String permissionDescription;

    private String url;

    private Date createDate;

    private String state;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "RolePermission",
//            joinColumns = {@JoinColumn(name = "pid")},inverseJoinColumns = {@JoinColumn(name = "rid")})
//    private List<Role> roles;



}
