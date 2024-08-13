package edu.poly.shop.repository;

import java.util.List;

import lombok.Data;

@Data
public class CommentThreadResponse {
    private String kind;
    private String etag;
    private List<Item> items;
    private PageInfo pageInfo;

    public static class Item {
        private String kind;
        private String etag;
        private String id;
        private Snippet snippet;

        public static class Snippet {
            private String videoId;
            private TopLevelComment topLevelComment;

            public static class TopLevelComment {
                private String kind;
                private String etag;
                private String id;
                private CommentSnippet snippet;

                public static class CommentSnippet {
                    private String videoId;
                    private String textDisplay;
                    private String authorDisplayName;
                    private String authorProfileImageUrl;
                    private String authorChannelUrl;
                    private String likeCount;
                    private String publishedAt;
                    private String updatedAt;
                }
            }
        }
    }

    public static class PageInfo {
        private int totalResults;
        private int resultsPerPage;
    }
}
