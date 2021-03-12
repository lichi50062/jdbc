package com.kaka.blog.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kaka
 */
@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String content;//留言內容
    private String avatar;//大頭貼
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    private Blog blog;
    @ManyToOne
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();
    private Boolean adminComment;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", parentComment=" + parentComment +
                ", childComments=" + childComments +
                ", adminComment=" + adminComment +
                '}';
    }
}
