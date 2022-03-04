package br.com.monitoratec.farol.auth.managers;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.redis.model.CachedTrustedToken;
import br.com.monitoratec.farol.redis.repository.CachedTrustedTokenRepository;
import br.com.monitoratec.farol.sql.model.user.LoginSession;
import br.com.monitoratec.farol.sql.model.user.SystemUser;
import br.com.monitoratec.farol.sql.repository.user.LoginSessionRepository;
import br.com.monitoratec.farol.utils.data.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AuthManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthManager.class);
    // One month
    public static final Long AUTHENTICATION_CACHE_VALIDITY = 1000 * 60 * 60 * 24 * 30L;

    private final LoginSessionRepository loginSessionRepository;
    private final CachedTrustedTokenRepository cachedTrustedTokenRepository;

    public AuthManager(LoginSessionRepository loginSessionRepository, CachedTrustedTokenRepository cachedTrustedTokenRepository) {
        this.loginSessionRepository = loginSessionRepository;
        this.cachedTrustedTokenRepository = cachedTrustedTokenRepository;
    }

    public String createNewSessionForUser(SystemUser systemUser, long sessionDurationMillis) {
        LOGGER.info("Creating new session for user: " + systemUser.toString());

        String userToken = TokenGenerator.genNewToken();
        Long validUntil = System.currentTimeMillis() + sessionDurationMillis;

        // Login session data
        LoginSession loginSession = new LoginSession();
        loginSession.setSystemUser(systemUser);
        loginSession.setValidUntilMillis(validUntil);
        loginSession.setAuthToken(userToken);

        loginSessionRepository.save(loginSession);

        return userToken;
    }

    public CachedTrustedToken solveAuthToken(String authToken) {
        // First try to get user info from redis cache
        CachedTrustedToken cachedToken = cachedTrustedTokenRepository.retrieve(authToken);
        if (cachedToken == null) {
            // There is no cached token, try to fetch DB
            LOGGER.info("Token was not found on cache");

            Optional<LoginSession> loginSessionOptional = loginSessionRepository.findByAuthToken(authToken);
            if (loginSessionOptional.isPresent() && loginSessionOptional.get().getValidUntilMillis() > System.currentTimeMillis()) {
                LOGGER.info("But Login session was found, what means the token really is valid (by time valid)");
                return this.bindAuthTokenToCache(authToken, loginSessionOptional.get());

            } else {
                LOGGER.info("And no Login session was found for this token, what means the token is invalid!");
                return null;
            }

        } else {
            // there is cached token
            LOGGER.info("Token was found on cache UserID -> {}", cachedToken.userCachedInfo.entityID);
            return cachedToken;
        }
    }

    private CachedTrustedToken bindAuthTokenToCache(String authToken, LoginSession loginSession) {
        Long tokenExpirationInterval = loginSession.getValidUntilMillis() - System.currentTimeMillis();

        SystemUser systemUser = loginSession.getSystemUser();
        AccessingEntity accessingEntity = AccessingEntity.fromSystemUser(systemUser);

        cachedTrustedTokenRepository.setToken(authToken, systemUser.getId(), accessingEntity, tokenExpirationInterval, TimeUnit.MILLISECONDS);

        return cachedTrustedTokenRepository.retrieve(authToken);
    }
}
