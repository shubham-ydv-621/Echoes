package com.shubham.Echoes.scheduler;

import com.shubham.Echoes.cache.AppCache;
import com.shubham.Echoes.entity.EchoesEntry;
import com.shubham.Echoes.entity.User;
import com.shubham.Echoes.enums.Sentiment;
import com.shubham.Echoes.repository.UserRepositoryImpl;
import com.shubham.Echoes.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    // Runs every Sunday at 9 AM
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<EchoesEntry> echoesEntries = user.getEchoesEntries();
            List<Sentiment> sentiments = echoesEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(EchoesEntry::getSentiment)
                    .collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                try {
                    emailService.sendEmail(
                            user.getEmail(),
                            "Sentiment for previous week",
                            "Your sentiment for the last 7 days: " + mostFrequentSentiment
                    );
                } catch (Exception e) {
                    // Log the error
                    System.err.println("Failed to send email to " + user.getEmail() + ": " + e.getMessage());
                }
            }
        }
    }

    // Clear cache every 10 minutes
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
