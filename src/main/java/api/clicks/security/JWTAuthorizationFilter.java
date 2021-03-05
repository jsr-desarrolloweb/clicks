package api.clicks.security;

import api.clicks.models.Player;
import api.clicks.repositories.PlayerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {


    private PlayerRepository playerRepository;

    public JWTAuthorizationFilter(ApplicationContext applicationContext) {
        this.playerRepository = applicationContext.getBean(PlayerRepository.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // TODO: No keys hardcoded!!!
        if(httpServletRequest.getHeader("Authorization")!=null) {
            if (!httpServletRequest.getHeader("Authorization").startsWith("Bearer ")) {
                SecurityContextHolder.clearContext();
            } else {
                String jwtToken = httpServletRequest.getHeader("Authorization").replace("Bearer ", "");
                try {
                    Claims claims = Jwts.parser().setSigningKey("pestillo".getBytes()).parseClaimsJws(jwtToken).getBody();
                    String name = claims.getSubject();
                    Player player = playerRepository.findByName(name)
                            .orElseThrow(EntityNotFoundException::new);
                    if(!player.getToken().equals(jwtToken))
                        throw new Exception();
                    Hibernate.initialize(player.getRole());
                    setUpSpringAuthentication(player);
                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setUpSpringAuthentication(Player player) {


        //TODO Crear roles
        Hibernate.initialize(player.getRole());

        //Save Roles in a Array
        //Add player to the rol
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(player.getRole());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(player, null,
                        roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
