package com.captain.contract.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

/**
 * @author : liuguang
 * @date : 2018/9/13 15:24
 * @escription :
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    //@Configuration
    public static class workwechatWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/workWechat/login").permitAll()
                    .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/invalidSession").permitAll()
                    .and()
                    .csrf().disable(); //关闭CSRF;
            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }
        private SessionRegistry sessionRegistry(){
            return new SessionRegistryImpl();
        }
        //session失效跳转
        private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
            return new SimpleRedirectSessionInformationExpiredStrategy("/invalidSession");
        }
    }

    //@Configuration
    public static class apiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/**").hasAnyAuthority("ROLE_ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/invalidSession").permitAll()
                    .and()
                    .csrf().disable(); //关闭CSRF;
            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        }
        private SessionRegistry sessionRegistry(){
            return new SessionRegistryImpl();
        }
        //session失效跳转
        private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
            return new SimpleRedirectSessionInformationExpiredStrategy("/invalidSession");
        }
    }

    @Configuration
    @Order(1)
    public static class adminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("adminUserService")
        UserDetailsService userDetailsService;
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //使用SHA-256+随机盐+密钥把用户输入的密码进行hash处理，得到密码的hash值，然后将其存入数据库中
            //每一次encode 密码不一致
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            return authenticationProvider;
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/admin/login").permitAll()
                    .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/admin/login") //管理登录页面
                    .failureUrl("/admin/login?error")
                    .defaultSuccessUrl("/admin/index")
                    .and()
                    .headers().frameOptions().disable()//支持 iframe
                    .and().csrf().disable(); //关闭CSRF;

            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
                    .expiredUrl("/admin/login")
                    .sessionRegistry(sessionRegistry());

            http
                    .logout()
                    .logoutSuccessUrl("/admin/login")
                    .permitAll()
                    .invalidateHttpSession(true);
        }

        private SessionRegistry sessionRegistry(){
            return new SessionRegistryImpl();
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("12"));
        System.out.println(bCryptPasswordEncoder.matches("12","$2a$10$QsgGMhrvlHOkPb5MBmA5N.OJbI/UcGaiOhGshFFH1jMP/zoli/2s."));
    }

}
