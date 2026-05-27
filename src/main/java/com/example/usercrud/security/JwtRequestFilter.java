package com.example.usercrud.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		String path = request.getServletPath();
		System.out.println("PATH = " + path);

		return path.startsWith("/api/auth") || path.startsWith("/api/json") || path.startsWith("/api/kafka")
				|| path.startsWith("/api/products") || path.equals("/api/users");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token Missing");
			return;
		}

		String token = header.substring(7);

		if (jwtUtil.validateToken(token)) {

			String username = jwtUtil.extractUsername(token);
			String role = jwtUtil.extractRole(token);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
					List.of(new SimpleGrantedAuthority("ROLE_" + role)));

			SecurityContextHolder.getContext().setAuthentication(auth);

		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid Token");
			return;
		}

		filterChain.doFilter(request, response);
	}
}