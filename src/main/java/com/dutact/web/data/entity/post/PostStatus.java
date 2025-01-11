package com.dutact.web.data.entity.post;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostStatus.Public.class, name = PostStatus.Public.TYPE_NAME),
        @JsonSubTypes.Type(value = PostStatus.Removed.class, name = PostStatus.Removed.TYPE_NAME)
})
public abstract class PostStatus {
    public static class Public extends PostStatus {
        public static final String TYPE_NAME = "public";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Removed extends PostStatus {
        public static final String TYPE_NAME = "removed";

        private String reason;
    }
}
