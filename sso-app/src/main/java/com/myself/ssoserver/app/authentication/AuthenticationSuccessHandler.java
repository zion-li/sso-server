package com.myself.ssoserver.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myself.ssoserver.app.service.CustomerLoginLogService;
import com.myself.ssoserver.properties.SecurityConstants;
import com.myself.ssoserver.util.IpUtil;
import com.myself.ssoserver.validate.ValidateCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * BasicAuthenticationFilter登录成功处理器，封装以及存储token
 *
 * @author Created by zion
 * @Date 2019/2/19.
 */
@Slf4j
@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    private String credentialsCharset = "UTF-8";

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 定义了令牌的创建、获取、刷新方法
     */
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ValidateCodeRepository sessionStrategy;

    @Autowired
    private CustomerLoginLogService customerLoginLogService;

    /**
     * 登录成功的处理
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("登录成功");

        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        //抽取和解码获取的Authorization
        String[] tokens = this.extractAndDecodeHeader(header, request);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        //1:成功拿到ClientDetails
        //流程 userRequest--携带ClientId-->clientDetailsService---->ClientDetails---->oAuth2Request
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId:" + clientId + " 对应信息不存在");
        } else if (!bCryptPasswordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }

        //2:构建TokenRequest
        //map是存储authentication内属性的,因为我们这里自带authentication,所以传空map即可
        //grantType是我们除了4中标准的授权模式之外自定义的一种
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        //3:构建Oauth2Request
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        //4：拼接 Oauth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        //5：通过认证获取令牌(DefaultTokenServices)
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        //6：记录用户登录信息
        if (StringUtils.equals(request.getRequestURI(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)) {
            String username = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_USERNAME);
            if (StringUtils.isNotBlank(username)) {
                customerLoginLogService.recordLoginSuccess(request.getRequestURI(), username, IpUtil.getIpAddr(request));
            }
        } else if (StringUtils.equals(request.getRequestURI(), SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE)) {
            String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
            if (StringUtils.isNotBlank(mobile)) {
                customerLoginLogService.recordLoginSuccess(request.getRequestURI(), mobile, IpUtil.getIpAddr(request));
            }
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));
    }

    /**
     * copy底层的源码
     *
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, this.getCredentialsCharset(request));
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

    public void setCredentialsCharset(String credentialsCharset) {
        Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
        this.credentialsCharset = credentialsCharset;
    }

    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return this.credentialsCharset;
    }
}
