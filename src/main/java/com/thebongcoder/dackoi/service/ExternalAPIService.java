package com.thebongcoder.dackoi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExternalAPIService {

    private static final String DICE_BEAR_API_URL = "https://api.dicebear.com/9.x/bottts/svg";


    public String generateAvatarUrl(String username) {
        try {
            log.info("Avatar request received:: {}", username);
            // Build the DiceBear URL with the username as a seed
            URIBuilder uriBuilder = new URIBuilder(DICE_BEAR_API_URL + "/" + username + ".png");
            uriBuilder.addParameter("size", "200"); // Optional: Set the size of the avatar
            uriBuilder.addParameter("background", "random"); // Optional: Set a background color
            return uriBuilder.build().toString();
        } catch (Exception e) {
            log.error("Error generating avatar URL for username: {} - Exception: {}", username, e.getMessage());
            throw new RuntimeException("Error generating avatar URL", e);
        }
    }
}
