package cn.com.taiji.actual.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lwl
 * @version 1.0
 * @date 2018/12/14 16:28
 */

@Entity
@Table(name = "discussion_group")
@Data
public class DiscussionGroup implements Serializable {

    private static final long serialVersionUID = -4379965422134487522L;
    @Id
    @GeneratedValue
    private Integer did;

    private String discussionName;

    //private UserInfo leader;

    @ManyToMany(mappedBy = "disGroupList", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserInfo> users;

    private Date createDate;

    private String state;

 /*   @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Article> articles;*/

}
