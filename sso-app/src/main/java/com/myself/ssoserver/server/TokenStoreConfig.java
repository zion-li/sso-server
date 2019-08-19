package com.myself.ssoserver.server;


import com.myself.ssoserver.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * token存储工具配置
 *
 * @author Created by zion
 * @Date 2019/3/22.
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "security-properties.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * prefix 文件前缀
     * name 最后一个点之后的属性名
     * havingValue 当这个属性是 xxx（jwt）的时候这个类都会生效
     * matchIfMissing true 默认下面的类是生效的
     */
    @Configuration
    @ConditionalOnProperty(prefix = "security-properties.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            //JwtTokenStore仅仅用来存取，不负责生成，生成jwt需要JwtAccessTokenConverter
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 转换成JWT
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //set密钥
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            //访问令牌转换器
            return accessTokenConverter;
        }

        /**
         * JWT增强器,往里面加内容加信息
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new TokenEnhancerConfig();
        }
    }
}
