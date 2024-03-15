package com.example.demo.src.post.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchPostingReq {
    private Long userId;
    private String content;
    private List<String> images;
    
}
