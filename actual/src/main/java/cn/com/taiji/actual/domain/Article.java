package cn.com.taiji.actual.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Barry
 * @version v1.0
 * @description 帖子实体类
 * @date created on 2018/12/17 15:24
 */

@Entity
@Table(name = "article")
@Data
public class Article  implements Serializable {

    private static final long serialVersionUID = -6392393758881133960L;
    @Id
    @GeneratedValue
    private Integer aid;

    private String aName;

    private byte[] aContent;

    private Date createDate;

    private String state;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "userInfo")
    private UserInfo userInfo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "DisGroup")
    private DiscussionGroup DisGroup;

    @Override
    public String toString() {
        return "Article{" +
                "aid=" + aid +
                ", aName='" + aName + '\'' +
                ", aContent=" + Arrays.toString(aContent) +
                ", createDate=" + createDate +
                ", state='" + state + '\'' +
                '}';
    }
}
