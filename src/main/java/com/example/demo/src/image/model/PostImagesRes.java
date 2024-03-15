package com.example.demo.src.image.model;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImagesRes {
    private List<String> urls;
    private Integer imageCount;

    public PostImagesRes(List<String> urls) {
        this.urls = urls;
        this.imageCount = urls.size();
    }
}
