package com.dutact.web.features.newsfeed.student.dtos;


import com.dutact.web.features.event.admin.dtos.post.PostDto;
import com.dutact.web.features.feedback.student.dtos.FeedbackDto;
import com.dutact.web.features.post.student.dtos.PostEventDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewsfeedElementDto.NewsfeedPostDto.class, name = NewsfeedElementDto.NewsfeedPostDto.TYPE_NAME),
        @JsonSubTypes.Type(value = NewsfeedElementDto.NewsfeedFeedbackDto.class, name = NewsfeedElementDto.NewsfeedFeedbackDto.TYPE_NAME),
})
public abstract class NewsfeedElementDto implements Comparable<NewsfeedElementDto> {
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(name = "StudentNewsfeedPostDto")
    public static class NewsfeedPostDto extends NewsfeedElementDto {
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

        @Override
        public int compareTo(NewsfeedElementDto object) {
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
    public static class NewsfeedFeedbackDto extends NewsfeedElementDto {
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

        @Override
        public int compareTo(NewsfeedElementDto object) {
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
