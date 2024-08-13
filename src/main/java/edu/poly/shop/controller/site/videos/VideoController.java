package edu.poly.shop.controller.site.videos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.repository.VideoResponse;
import edu.poly.shop.service.EmailService;
import edu.poly.shop.service.YouTubeService;

@Controller
@RequestMapping("site/video")
public class VideoController {

    private final YouTubeService youTubeService;
    private final EmailService emailService;

    public VideoController(YouTubeService youTubeService, EmailService emailService) {
        this.youTubeService = youTubeService;
        this.emailService = emailService;
    }

    @GetMapping("/videos")
    public String getVideos(Model model) {
        var videoResponse = youTubeService.getRecentVideos();
        if (videoResponse != null && videoResponse.getItems() != null) {
            model.addAttribute("videos", videoResponse.getItems());
        } else {
            System.out.println("No videos found");
        }
        return "site/video/videos";
    }

    @GetMapping("/search")
    public String searchVideos(@RequestParam("query") String query, Model model) {
        VideoResponse response = youTubeService.searchVideos(query);
        model.addAttribute("videos", response.getItems());
        return "site/video/videos";
    }

    @PostMapping("/share")
    public String shareVideo(@RequestParam("videoId") String videoId, @RequestParam("email") String email,
            Model model) {
        emailService.sendVideoShareEmail(email, videoId);
        model.addAttribute("message", "Video shared successfully!");
        return "redirect:/site/video/videos";
    }

}
