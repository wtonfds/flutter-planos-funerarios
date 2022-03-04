package br.com.monitoratec.farol.redis.model;

import br.com.monitoratec.farol.auth.model.AccessingEntity;

import java.io.Serializable;

public class CachedTrustedToken implements Serializable {
    public static class UserCachedInfo {
        public final Long entityID;
        public final AccessingEntity accessingEntity;

        private UserCachedInfo() {
            this.entityID = null;
            this.accessingEntity = null;
        }

        private UserCachedInfo(Long entityID, AccessingEntity accessingEntity) {
            this.entityID = entityID;
            this.accessingEntity = accessingEntity;
        }
    }

    public final String authToken;
    public final UserCachedInfo userCachedInfo;

    private CachedTrustedToken() {
        this.authToken = null;
        this.userCachedInfo = null;
    }

    public CachedTrustedToken(String authToken, Long entityID, AccessingEntity accessingEntity) {
        this.userCachedInfo = new UserCachedInfo(entityID, accessingEntity);
        this.authToken = authToken;
    }
}
