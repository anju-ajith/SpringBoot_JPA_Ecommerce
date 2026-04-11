package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	private UserDetailsService uds;
	private UserDetailsService adminUserDetailsService;
	private BCryptPasswordEncoder encoder;

	public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService uds,
			@Qualifier("adminServiceImpl") UserDetailsService adminUserDetailsService, BCryptPasswordEncoder encoder) {

		this.uds = uds;
		this.adminUserDetailsService = adminUserDetailsService;
		this.encoder = encoder;
	}

	@Bean
	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/userRegistration", "/userLoginPage", "/shopNowNewborn", "/shopNowBoysFashion",
						"/shopNowGirlsFashion", "/contacts", "/registerUser", "/wishlistpage", "/login",
						"/newbornFashionType", "/boysFashionType", "/girlsFashionType", "/resetUserPassword",
						"/filterProducts", "/filterProductsBoysFashion", "/filterProductsGirlsFashion","/getproductDetailsPage","/send-otp","/verify-otp","/userOtpLogin","/css/**",
						"/js/**", "/img/**", "/fonts/**", "/assets/**", "/sass/**")
				.permitAll()

				.requestMatchers("/checkout", "/orderSuccess", "/cart", "/userOrderHistory").hasAuthority("CUSTOMER")

				.anyRequest().authenticated()

		).formLogin(form -> form.loginPage("/userLoginPage").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
				.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.clearAuthentication(true).permitAll())
				.authenticationProvider(getAuthenticationProvider());

		return http.build();

	}

	@Bean
	@Order(1)
	public SecurityFilterChain adminSecurity(HttpSecurity http) throws Exception {

		http.securityMatcher("/admin/**", "/adminLogin", "/loginAdmin", "/adminLogout")

				.authorizeHttpRequests(auth -> auth.requestMatchers("/adminLogin", "/loginAdmin").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN").anyRequest().authenticated())

				.formLogin(form -> form.loginPage("/adminLogin").loginProcessingUrl("/loginAdmin")
						.defaultSuccessUrl("/admin", true))
				.logout(logout -> logout.logoutUrl("/adminLogout").logoutSuccessUrl("/adminLogin")
						.invalidateHttpSession(true).clearAuthentication(true))

				.authenticationProvider(adminAuthProvider());

		return http.build();
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(uds);
		authenticationProvider.setPasswordEncoder(encoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationProvider adminAuthProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(adminUserDetailsService);
		provider.setPasswordEncoder(encoder);
		return provider;
	}

}
