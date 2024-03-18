package com.example.demo.src.post.model;

import com.example.demo.common.entity.BaseEntity.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostingReq {
    private String nickname;
    private String createdAt;
    private State state;
}
