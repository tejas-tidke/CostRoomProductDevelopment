package com.htc.productdevelopment.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private static final Logger logger =
        LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.service-account-path:keys/firebase.json}")
    private String firebaseServiceAccountPath;

    @PostConstruct
    public void initializeFirebase() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                logger.info("Firebase already initialized");
                return;
            }

            // Try multiple possible locations for the service account file
            String[] possiblePaths = {
                firebaseServiceAccountPath,                        // From application properties
                "./keys/firebase.json",                           // Relative to working directory
                "../keys/firebase.json",                          // One level up
                "../../keys/firebase.json",                       // Two levels up
                "../../../keys/firebase.json",                    // Three levels up
                "/home/costroom/keys/firebase.json",              // Absolute path for production
                "/root/keys/firebase.json",                       // Alternative absolute path
                "keys/firebase.json"                              // Direct relative path
            };
            
            FileInputStream serviceAccountStream = null;
            for (String path : possiblePaths) {
                try {
                    serviceAccountStream = new FileInputStream(path);
                    logger.info("Found Firebase service account file at: " + path);
                    break;
                } catch (IOException e) {
                    // Try next path
                    continue;
                }
            }
            
            if (serviceAccountStream == null) {
                throw new IOException("Firebase service account file not found in any of the expected locations");
            }
            
            GoogleCredentials credentials = GoogleCredentials
                .fromStream(serviceAccountStream);

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

            FirebaseApp.initializeApp(options);
            logger.info("Firebase initialized successfully");

        } catch (IOException e) {
            logger.error("Failed to initialize Firebase with service account file", e);
            
            // Fallback: try with application default credentials
            try {
                GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

                FirebaseApp.initializeApp(options);
                logger.info("Firebase initialized successfully with default credentials");
            } catch (Exception fallbackException) {
                logger.error("Failed to initialize Firebase with default credentials", fallbackException);
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Firebase", e);
        }
    }
}

