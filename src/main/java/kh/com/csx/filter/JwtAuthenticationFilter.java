package kh.com.csx.filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.com.csx.service.auth.JwtService;
import kh.com.csx.constant.Constant;
import kh.com.csx.response.Response;
import kh.com.csx.response.ErrorResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(Constant.JWT_AUTHORIZATION_HEADER);
            final String jwt;
            final String username;
            if(authHeader == null || !authHeader.startsWith(Constant.JWT_AUTHORIZATION_HEADER_STARTER)) {
                filterChain.doFilter(request,response);
                return;
            }
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt,userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
        } catch (RuntimeException e){
            LOGGER.error(e.getMessage(), e);
            Response errorResponse;

            ObjectMapper mapper = new ObjectMapper();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            if (e instanceof UsernameNotFoundException) {
                errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "User not found");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            else if(e instanceof ExpiredJwtException) {
                errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Your token has expired. Please login again.");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            else {
                errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

            String jsonResponse = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
        }
    }
}
