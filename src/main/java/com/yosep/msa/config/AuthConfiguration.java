package com.yosep.msa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.yosep.msa.account.YoggaebiUserService;

@Configuration
@EnableAuthorizationServer
//@SpringBootApplication
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter{
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ResourceServerProperties resourceServerProperties;
	
	@Autowired
	YoggaebiUserService yoggaebiUserService;
	
	@Autowired
	TokenStore tokenStore;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.passwordEncoder(passwordEncoder);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
//		clients.withClientDetails(clientDetailsService);
		clients.inMemory()
		.withClient("yoggaebi")
		.authorizedGrantTypes("password","refresh_token")
		.scopes("read","write")
		.secret(this.passwordEncoder.encode("pass"))
		.accessTokenValiditySeconds(10 * 60)
		.refreshTokenValiditySeconds(6 * 10 * 60);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(endpoints);
//		endpoints.accessTokenConverter(jwtAccessTokenConverter()).authenticationManager(authenticationManager);
		
		endpoints.authenticationManager(authenticationManager)
		.accessTokenConverter(jwtAccessTokenConverter())
		.userDetailsService(yoggaebiUserService)
		.tokenStore(tokenStore);
	}
	
//	@Bean
//	@Primary
//	public JdbcClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
//		return new JdbcClientDetailsService(dataSource);
//	}
	
	@Bean
	public AccessTokenConverter jwtAccessTokenConverter() {
		// TODO Auto-generated method stub
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());
		
		return accessTokenConverter;
	}
}
