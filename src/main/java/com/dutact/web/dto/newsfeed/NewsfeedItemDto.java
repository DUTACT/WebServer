package com.dutact.web.dto.newsfeed;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewsfeedItemDto.NewsfeedPostDto.class, name = NewsfeedItemDto.NewsfeedPostDto.TYPE_NAME),
        @JsonSubTypes.Type(value = NewsfeedItemDto.NewsfeedFeedbackDto.class, name = NewsfeedItemDto.NewsfeedFeedbackDto.TYPE_NAME),
})
public abstract class NewsfeedItemDto implements Comparable<NewsfeedItemDto> {
    @Nullable
    @JsonProperty("likedAt")
    protected LocalDateTime likedAt;
    @Nullable
    @JsonProperty("likedNumber")
    protected Integer likeNumber;

    @Nullable
    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(@Nullable Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    // Getter and setter for likedAt
    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(name = "StudentNewsfeedPostDto")
    public static class NewsfeedPostDto extends NewsfeedItemDto {
        public static final String TYPE_NAME = "post";

        @JsonProperty("organizer")
        private NewsfeedUserDto organizer;

        @JsonProperty("event")
        private NewsfeedEventDto event;

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("content")
        private String content;

        @JsonProperty("postedAt")
        private LocalDateTime postedAt;

        @JsonProperty("coverPhotoUrl")
        private String coverPhotoUrl;

        @JsonProperty("coverPhotoUrls")
        private List<String> coverPhotoUrls;

        @Override
        public int compareTo(NewsfeedItemDto object) {
            if (object instanceof NewsfeedPostDto) {
                NewsfeedPostDto postCompareTo = (NewsfeedPostDto) object;
                return this.postedAt.compareTo(postCompareTo.postedAt);
            } else {
                NewsfeedFeedbackDto postCompareTo = (NewsfeedFeedbackDto) object;
                return this.postedAt.compareTo(postCompareTo.postedAt);
            }
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(name = "StudentNewsfeedFeedbackDto")
    public static class NewsfeedFeedbackDto extends NewsfeedItemDto {
        public static final String TYPE_NAME = "feedback";

        @JsonProperty("student")
        private NewsfeedUserDto student;

        @JsonProperty("event")
        private NewsfeedEventDto event;

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("content")
        private String content;

        @JsonProperty("postedAt")
        private LocalDateTime postedAt;

        @JsonProperty("coverPhotoUrl")
        private String coverPhotoUrl;

        @JsonProperty("coverPhotoUrls")
        private List<String> coverPhotoUrls;

        @Override
        public int compareTo(NewsfeedItemDto object) {
            if (object instanceof NewsfeedPostDto) {
                NewsfeedPostDto postCompareTo = (NewsfeedPostDto) object;
                return this.postedAt.compareTo(postCompareTo.postedAt);
            } else {
                NewsfeedFeedbackDto postCompareTo = (NewsfeedFeedbackDto) object;
                return this.postedAt.compareTo(postCompareTo.postedAt);
            }
        }
    }
}
