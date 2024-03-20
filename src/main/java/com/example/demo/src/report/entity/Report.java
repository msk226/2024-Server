package com.example.demo.src.report.entity;

import com.example.demo.common.Constant.ReportReason;
import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "REPORT")
public class Report extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportDetail;

    private ReportReason reportReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_user_id")
    private User reportUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_post_id")
    private Post reportPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_comment_id")
    private Comment reportComment;

    private Integer reportCount;
}
