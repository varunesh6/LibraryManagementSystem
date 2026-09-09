package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository users; private final AuthService auth;
    public OAuth2SuccessHandler(UserRepository users,AuthService auth){this.users=users;this.auth=auth;}
    @Override @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,Authentication authentication)throws IOException,ServletException{
        OAuth2User oauth=(OAuth2User)authentication.getPrincipal();
        String email=oauth.getAttribute("email"); String name=oauth.getAttribute("name"); String providerId=oauth.getAttribute("sub");
        if(email==null)throw new ServletException("Google did not provide an email");
        User user=users.findByEmail(email.toLowerCase()).orElseGet(()->users.save(new User(name==null?"Google User":name,email.toLowerCase(),null,Provider.GOOGLE,providerId,Role.USER)));
        if(user.getProvider()!=Provider.GOOGLE && user.getProviderId()==null){user.setProvider(Provider.GOOGLE);user.setProviderId(providerId);users.save(user);}
        AuthResponse tokens=auth.tokens(user);
        String target="/oauth2/success?accessToken="+URLEncoder.encode(tokens.accessToken(),StandardCharsets.UTF_8)+"&refreshToken="+URLEncoder.encode(tokens.refreshToken(),StandardCharsets.UTF_8);
        getRedirectStrategy().sendRedirect(request,response,target);
    }
}