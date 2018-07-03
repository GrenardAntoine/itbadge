package com.itescia.itbadge.security;

import java.awt.List;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.itescia.itbadge.domain.Authority;
import com.itescia.itbadge.domain.User;
import com.itescia.itbadge.repository.UserRepository;
import com.itescia.itbadge.service.MailService;
import com.itescia.itbadge.service.UserService;
import com.itescia.itbadge.service.dto.UserDTO;
import com.itescia.itbadge.web.rest.UserResource;


@Component
@Primary
public class CustomAuthenticationManager implements AuthenticationManager {
	

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    LdapAuthenticationProvider provider = null;

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    private final UserRepository userRepository;

    @Autowired
    private final LdapContextSource ldapContextSource;

    public CustomAuthenticationManager(UserRepository userRepository, LdapContextSource ldapContextSource) {
        this.userRepository = userRepository;
        this.ldapContextSource = ldapContextSource;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        log.debug("AUTHENTICATION Login" + authentication.getName());
        log.debug("AUTHENTICATION Password" + authentication.getCredentials().toString());

        BindAuthenticator bindAuth = new BindAuthenticator(ldapContextSource);
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
                "", "(uid={0})",
                ldapContextSource);
        try{
            bindAuth.setUserSearch(userSearch);
            bindAuth.afterPropertiesSet();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CustomAuthenticationManager.class.getName()).log(null);
        }
        provider = new LdapAuthenticationProvider(bindAuth);
        provider.setUserDetailsContextMapper(new UserDetailsContextMapper() {
            @Override
            public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> clctn) {
                //Optional<User> isUser = userRepository.findOneWithAuthoritiesByLogin(username);
            	Optional<User> isUser = userRepository.findOneByLogin(username);             
                
                if (!isUser.isPresent())
                {
                	try {
                		UserDTO uDTO = new UserDTO();
                		uDTO.setLogin(authentication.getName());
                		uDTO.setActivated(true);
                		uDTO.setFirstName(authentication.getName());
                		uDTO.setLastName(authentication.getName());
                		uDTO.setEmail(authentication.getName()+"@edu.itescia.fr");
                		Set<String> gA = new HashSet<String>();
                		gA.add("ROLE_USER");
                		uDTO.setAuthorities(gA);
                		
                		System.err.println(uDTO.getEmail());
                		System.err.println(uDTO.getFirstName());
						new UserResource(userService, userRepository, mailService).createUser(uDTO);
						isUser = userRepository.findOneByLogin(username);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                }
                
                final User user = isUser.get();
                Set<Authority> userAuthorities = user.getAuthorities();
                System.err.println(userAuthorities.toString());
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for(Authority a: userAuthorities){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                            a.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
                  return new org.springframework.security.core.userdetails.User(
                    username, "1" , grantedAuthorities);    
            }

            @Override
            public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {

            }
        });
        return provider.authenticate(authentication);
    }
}
