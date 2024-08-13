package edu.poly.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.poly.shop.repository.VideoResponse;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.channel.id}")
    private String channelId;


    private final RestTemplate restTemplate = new RestTemplate();
    private final String SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";
    private final String COMMENT_THREADS_URL = "https://www.googleapis.com/youtube/v3/commentThreads";
    private final String COMMENTS_URL = "https://www.googleapis.com/youtube/v3/comments";

    public VideoResponse getRecentVideos() {
        String url = String.format(
                "https://www.googleapis.com/youtube/v3/search?key=%s&channelId=%s&part=snippet,id&order=date&maxResults=12",
                apiKey, channelId);
        return restTemplate.getForObject(url, VideoResponse.class);
    }

    public VideoResponse searchVideos(String query) {
        String url = SEARCH_URL + "?part=snippet&type=video&q=" + query + "&channelId=" + channelId + "&key=" + apiKey;
        return restTemplate.getForObject(url, VideoResponse.class);
    }

    // public VideoSearchResponse searchVideos(String query) {
    // try {
    // String url = SEARCH_URL + "?part=snippet&type=video&q=" + query + "&key=" +
    // apiKey;
    // return restTemplate.getForObject(url, VideoSearchResponse.class);
    // } catch (HttpClientErrorException e) {
    // // Log lỗi và xử lý ngoại lệ
    // System.err.println("API Request Error: " + e.getMessage());
    // throw new RuntimeException("Failed to search videos", e);
    // }
    // }

}