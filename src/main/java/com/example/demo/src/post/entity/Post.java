package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.image.entity.Image;
import com.example.demo.src.post.model.PatchPostingReq;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Getter
@Audited
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "POST")
public class Post extends BaseEntity{

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection
    private List<String> hashTags = new ArrayList<>();

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "reportPost", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();


    public void update(PatchPostingReq patchPostingReq){
        this.content = patchPostingReq.getContent();
        this.images = patchPostingReq.getImages();
    }
    public void setUser(User user) {
        if (this.author == user) {
            return;
        }
        if (this.author != null) {
            this.author.removePost(this);
        }
        // 새로운 사용자와 연관 관계 설정
        this.author = user;
        // 새로운 사용자에게 해당 게시물을 추가
        if (user != null) {
            user.addPost(this);
        }
    }

    public void delete( ){
        this.state = State.INACTIVE;
    }

    public void addComment(Comment comment){this.comments.add(comment);}

    public void addReport(Report report){this.reports.add(report);}

}
