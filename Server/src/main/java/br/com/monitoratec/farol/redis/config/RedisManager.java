package br.com.monitoratec.farol.redis.config;

import br.com.monitoratec.farol.redis.model.CachedTrustedToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

@Configuration
@EnableRedisRepositories
public class RedisManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisManager.class);

    private final LettuceConnectionFactory lettuceConnectionFactory;

    public RedisManager(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @PostConstruct
    public void init() {
        LOGGER.warn("RedisUrl: {}:{}", lettuceConnectionFactory.getHostName(), lettuceConnectionFactory.getPort());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(this.lettuceConnectionFactory);

        template.setExposeConnection(true);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, CachedTrustedToken> trustedTokenRedisTemplate() {
        RedisTemplate<String, CachedTrustedToken> trustedTokenTemplate = new RedisTemplate<>();
        trustedTokenTemplate.setKeySerializer(new StringRedisSerializer());
        trustedTokenTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        trustedTokenTemplate.setConnectionFactory(this.lettuceConnectionFactory);

        trustedTokenTemplate.setExposeConnection(true);
        trustedTokenTemplate.afterPropertiesSet();

        return trustedTokenTemplate;
    }

    @Bean
    public RedisTemplate<String, Boolean> booleanRedisTemplate() {
        RedisTemplate<String, Boolean> authenticatedUserTemplate = new RedisTemplate<>();
        authenticatedUserTemplate.setKeySerializer(new StringRedisSerializer());
        authenticatedUserTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        authenticatedUserTemplate.setConnectionFactory(this.lettuceConnectionFactory);

        authenticatedUserTemplate.setExposeConnection(true);
        authenticatedUserTemplate.afterPropertiesSet();

        return authenticatedUserTemplate;
    }
}
