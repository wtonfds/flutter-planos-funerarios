package br.com.monitoratec.farol.redis.repository;

import br.com.monitoratec.farol.auth.managers.AuthManager;
import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.redis.model.CachedTrustedToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CachedTrustedTokenRepository {
    private static final String TRUSTED_TOKEN_PREFIX = "TrustedToken_";
    private static final String TRACKING_TRUSTED_TOKEN_PREFIX = "TrackingTrustedToken_";

    private final RedisTemplate<String, CachedTrustedToken> cachedTrustedTokenRedisTemplate;
    private final RedisTemplate<String, Boolean> booleanRedisTemplate;

    public CachedTrustedTokenRepository(RedisTemplate<String, CachedTrustedToken> cachedTrustedTokenRedisTemplate, RedisTemplate<String, Boolean> booleanRedisTemplate) {
        this.cachedTrustedTokenRedisTemplate = cachedTrustedTokenRedisTemplate;
        this.booleanRedisTemplate = booleanRedisTemplate;
    }

    // Helper to let set the token with the default validity
    public void setToken(String authToken, Long userID, AccessingEntity accessingEntity) {
        this.setToken(authToken, userID, accessingEntity, AuthManager.AUTHENTICATION_CACHE_VALIDITY, TimeUnit.MILLISECONDS);
    }

    public void setToken(String authToken, Long userID, AccessingEntity accessingEntity, Long validityInterval, TimeUnit timeUnit) {
        CachedTrustedToken cachedTrustedToken = new CachedTrustedToken(authToken, userID, accessingEntity);

        //NOTE: check if set(value, timeout, timeUnit) has the same effect
        String redisTrackingKey = this.buildTrackingKey(userID, authToken);
        this.booleanRedisTemplate.boundValueOps(redisTrackingKey).set(true);
        this.booleanRedisTemplate.expire(redisTrackingKey, validityInterval, timeUnit);

        String redisTokenKey = this.buildAuthKey(authToken);
        this.cachedTrustedTokenRedisTemplate.boundValueOps(redisTokenKey).set(cachedTrustedToken);
        this.cachedTrustedTokenRedisTemplate.expire(redisTokenKey, validityInterval, timeUnit);
    }

    private String buildTrackingKey(Long userID, String authToken) {
        return TRACKING_TRUSTED_TOKEN_PREFIX + userID.toString() + "_" + authToken;
    }

    private String buildAuthKey(String authToken) {
        return TRUSTED_TOKEN_PREFIX + authToken;
    }

    public CachedTrustedToken retrieve(String token) {
        CachedTrustedToken cachedTrustedToken = null;

        String key = this.buildAuthKey(token);
        try {
            cachedTrustedToken = this.cachedTrustedTokenRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cachedTrustedToken;
    }

    public void clear(String token) {
        CachedTrustedToken cachedTrustedToken = this.retrieve(token);

        if (cachedTrustedToken != null) {
            Long entityID = cachedTrustedToken.userCachedInfo.entityID;
            String redisTrackingKey = this.buildTrackingKey(entityID, token);
            String redisTokenKey = this.buildAuthKey(token);

            this.booleanRedisTemplate.delete(redisTrackingKey);
            this.cachedTrustedTokenRedisTemplate.delete(redisTokenKey);
        }
    }
}
