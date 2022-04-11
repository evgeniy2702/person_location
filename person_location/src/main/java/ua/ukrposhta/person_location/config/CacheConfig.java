package ua.ukrposhta.person_location.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.cache.Caching;
import java.io.IOException;
import java.net.URL;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    @Value("${ehcache.config.path}")
    private String ehCacheConfigPath;

    @Bean
    public JCacheCacheManager cacheManager() throws IOException {
        return new JCacheCacheManager(ehCacheManager());
    }

    @Bean(destroyMethod = "close")
    public javax.cache.CacheManager ehCacheManager() throws IOException {
        URL url = new ClassPathResource(ehCacheConfigPath).getURL();
        log.info("url cahce {}", url );
        XmlConfiguration xmlConfig = new XmlConfiguration(url);
        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();
        provider.getCacheManager(provider.getDefaultURI(),xmlConfig).getCacheNames()
                .forEach(System.out::println);
        return provider.getCacheManager(provider.getDefaultURI(), xmlConfig);
    }
}

