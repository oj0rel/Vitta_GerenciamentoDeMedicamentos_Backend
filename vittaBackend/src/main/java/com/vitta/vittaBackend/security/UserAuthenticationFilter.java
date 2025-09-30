package com.vitta.vittaBackend.security;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import com.vitta.vittaBackend.service.security.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // pula validação de token para endpoints públicos
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            try {
//                String subject = jwtTokenService.getSubjectFromToken(token);
                Integer userId = jwtTokenService.getUserIdFromToken(token);
                Optional<Usuario> maybeUser = usuarioRepository.findById(userId);

                if (maybeUser.isPresent()) {
                    Usuario user = maybeUser.get();
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                // loga o erro, mas não bloqueia o request aqui
                logger.error("Erro ao validar token: " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    // Verifica se o endpoint é público (não precisa de token)
    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // libera POST /api/usuario/cadastrarNovoUsuario e POST /api/usuario/login
        return (path.equals("/api/usuario/cadastrarNovoUsuario") && method.equalsIgnoreCase("POST")) ||
                (path.equals("/api/usuario/login") && method.equalsIgnoreCase("POST"));
    }

}
