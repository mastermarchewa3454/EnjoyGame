package com.enjoy.mathero.security;

import com.enjoy.mathero.io.entity.RoleEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for authorization process
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;


    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository){
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    /**
     * Method to filter requests without authorization header.
     *
     * @param request incoming request
     * @param response response that should be send to user
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Method to generate user form authorization token in header.
     *
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if(token != null){
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if(user != null){
                UserEntity userEntity = userRepository.findByUsername(user);
                List<GrantedAuthority> roles = new ArrayList<>();
                for(RoleEntity role: userEntity.getRoles()){
                    roles.add(new SimpleGrantedAuthority(role.getRoleName()));
                }

                return new UsernamePasswordAuthenticationToken(user, null, roles);
            }
        }

        return null;
    }
}
