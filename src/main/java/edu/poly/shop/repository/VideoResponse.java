package edu.poly.shop.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    @JsonProperty("items")
    private List<VideoItem> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VideoItem {
        @JsonProperty("id")
        private VideoId id;

        @JsonProperty("snippet")
        private Snippet snippet;
        
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class VideoId {
            @JsonProperty("videoId")
            private String videoId;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Snippet {
            @JsonProperty("title")
            private String title;

            @JsonProperty("description")
            private String description;

            @JsonProperty("thumbnails")
            private Thumbnails thumbnails;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Thumbnails {
                @JsonProperty("default")
                private Thumbnail defaultThumbnail;

                @Data
                @AllArgsConstructor
                @NoArgsConstructor
                public static class Thumbnail {
                    @JsonProperty("url")
                    private String url;
                }
            }
        }
    }
}

