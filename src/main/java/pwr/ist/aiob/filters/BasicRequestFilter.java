package pwr.ist.aiob.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pwr.ist.aiob.model.CustomUserDetails;
import pwr.ist.aiob.service.CustomUserDetailsService;
import pwr.ist.aiob.util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class BasicRequestFilter extends OncePerRequestFilter {
    @Resource
    CustomUserDetailsService userDetailsService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");


        if(header != null && header.startsWith("Basic ")) {
            String encoded = header.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(encoded);
            String decodedString = new String(decodedBytes);

            int separator = decodedString.indexOf(":");
            String username = decodedString.substring(0,separator);
            String password = decodedString.substring(separator+1);

            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, userDetails.getPassword())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
