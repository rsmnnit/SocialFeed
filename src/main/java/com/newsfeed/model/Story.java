package com.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Story {
    @Setter
    private String storyId;
    @NonNull
    private String postOwnerUserName;
    @NonNull
    private String content;
    private Long creationTimeMillis;
    private Double latitude;
    private Double longitude;
    private List<String> topics;
    private Integer likes;
}
