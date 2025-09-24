package com.cisco.shopapp.cfg;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final CacheManager cacheManager;

    //https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions

//    @Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0 0/30 * * * *") // every 30 min
    public void clearCache() {
        System.out.println("Called Clear Cache!!!");
            cacheManager.getCacheNames().forEach(name -> {
                cacheManager.getCache(name).clear();
            });
    }
}
