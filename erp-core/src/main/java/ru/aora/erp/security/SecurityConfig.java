package ru.aora.erp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.aora.erp.domain.service.user.UserAuthorityCacheService;
import ru.aora.erp.domain.service.user.UserService;
import ru.aora.erp.presentation.controller.dashboard.DashboardUrl;

import java.util.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String ALL_JS_MAPPING = "/js/**";
    private static final String ALL_CSS_MAPPING = "/css/**";
    private static final String ALL_ICONS_MAPPING = "/icons/**";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthorityCacheService authorityCache;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(
            UserService userService,
            PasswordEncoder passwordEncoder,
            UserAuthorityCacheService authorityCache,
            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler // Add this line
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authorityCache = authorityCache;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler; // Add this line
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Entering the configure method for AuthenticationManagerBuilder"); // Add this line
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

        logger.info("Exiting the configure method for AuthenticationManagerBuilder"); // Add this line
//        String apass = passwordEncoder.encode("a");
//        String upass = passwordEncoder.encode("u");
//        System.out.println("a:\n" + apass + "\nu:\n"+upass);
//
//        var u = userService.loadUserByUsername("u");
//        var a = userService.loadUserByUsername("a");
//
//        System.out.println(passwordEncoder.matches("a", a.getPassword()));
//        System.out.println(passwordEncoder.matches("u", u.getPassword()));
//        auth.inMemoryAuthentication()
//                .withUser("a")
//                .password(apass)
//                .authorities(DashboardAuthorityUrlMap.ALL.getAuthority())
//                .and()
//                .withUser("u")
//                .password(upass)
//                .authorities(DashboardAuthorityUrlMap.SEE_TEST.getAuthority());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Entering the configure method"); // Add this line


        http.csrf().disable();
        var urlRegistry = http.authorizeRequests()
                .antMatchers(ALL_CSS_MAPPING).permitAll()
                .antMatchers(ALL_JS_MAPPING).permitAll()
                .antMatchers(ALL_ICONS_MAPPING).permitAll()
                .antMatchers(DashboardUrl.LOGIN_MAPPING).permitAll()
                .antMatchers(DashboardUrl.LOGOUT_MAPPING).permitAll();
        urlRegistry.antMatchers(DashboardUrl.MAPPING).permitAll();
        defineAuthoritiesMapping(urlRegistry, authorityCache.urlAuthorityMap()); //todo enable user security
        urlRegistry.anyRequest().authenticated(); //todo enable user security

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.formLogin()
                .loginPage(DashboardUrl.LOGIN_MAPPING)
                .successHandler(customAuthenticationSuccessHandler) // Set the custom success handler
                .defaultSuccessUrl(DashboardUrl.MAPPING)
                .successForwardUrl(DashboardUrl.MAPPING)
                .permitAll();
        http.logout()
                .clearAuthentication(true)
                .logoutUrl(DashboardUrl.LOGOUT_MAPPING)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher(DashboardUrl.LOGOUT_MAPPING))
                .logoutSuccessUrl(DashboardUrl.LOGIN_MAPPING)
                .permitAll();
        logger.info("Exiting the configure method"); // Add this line
    }

    private void defineAuthoritiesMapping(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry,
            Map<? extends GrantedAuthority, Set<String>> authoritySetMap
    ) {
        Map<String, Set<String>> urlAuthorities = toUrlAuthorities(authoritySetMap);
        for (var entry : Objects.requireNonNull(urlAuthorities.entrySet())) {
            var url = entry.getKey();
            var authorities = entry.getValue().toArray(new String[0]);
            registry.antMatchers(url)
                    .hasAnyAuthority(authorities);
        }
    }

    private static Map<String, Set<String>> toUrlAuthorities(Map<? extends GrantedAuthority, Set<String>> authorityUrls) {
        Map<String, Set<String>> urlAuthorities = new HashMap<>();
        for (var entry : authorityUrls.entrySet()) {
            for (var url : entry.getValue()) {
                var authorities = urlAuthorities.computeIfAbsent(url, k -> new HashSet<>());
                authorities.add(entry.getKey().getAuthority());
                urlAuthorities.put(url, authorities);
            }
        }
        return urlAuthorities;
    }
}