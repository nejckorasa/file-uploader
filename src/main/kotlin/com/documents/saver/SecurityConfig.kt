package com.documents.saver

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer


/**
 * @author Nejc Korasa
 */

@Configuration
class SecurityConfig {


    @Profile("no-auth")
    @Configuration("noAuthConf")
    internal class NoAuth : WebSecurityConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .cors().and()
                    .httpBasic().disable()
                    .csrf().disable()
        }
    }


    @Profile("oauth2-auth")
    @Configuration("oauth2Conf")
    @EnableResourceServer
    internal class OAuth2 : WebSecurityConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                    .cors().and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .httpBasic().disable()
                    .csrf().disable()
                    .antMatcher("/**").authorizeRequests().anyRequest().authenticated()
        }
    }
}