package com.kaka.blog.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Kaka
 */
@Entity
@Data
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;//標題
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;//內容
    private String firstPicture;//首頁圖
    private String flag;//標註
    private Integer views;//瀏覽次數
    private boolean commentable;//留言開啟
    private boolean published;//發文或保存
    private boolean recommend;//是否推薦
    @Column(name = "creat_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//發文時間
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改時間
    @ManyToOne
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
    @Transient
    private Long typeId;
    @Transient
    private String tagIds;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    /**
     * 把tagid處理成1,2,3
      * @param tags
     * @return
     */
    private String tagsToIds(List<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        if (tags != null) {
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    sb.append(",");
                } else {
                    flag = true;
                }
                sb.append(tag.getId());
            }
            return sb.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", typeId=" + typeId +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
