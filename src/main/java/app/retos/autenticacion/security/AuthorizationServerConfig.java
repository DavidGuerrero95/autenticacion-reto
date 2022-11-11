package app.retos.autenticacion.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Base64;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permiteAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    @PostMapping("/login/autorizacion/token")
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("flutter-reto").secret(passwordEncoder.encode("udea")).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accesTokenConverter()));

        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(accesTokenConverter()).tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accesTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accesTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(Base64.getEncoder().encodeToString("codigo_secreto_ok_123456789".getBytes()));
        return tokenConverter;
    }

}
