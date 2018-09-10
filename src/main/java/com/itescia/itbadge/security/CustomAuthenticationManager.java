package com.itescia.itbadge.security;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.naming.NamingException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.itescia.itbadge.domain.Authority;
import com.itescia.itbadge.domain.User;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.repository.CoursRepository;
import com.itescia.itbadge.repository.GroupeRepository;
import com.itescia.itbadge.repository.UserRepository;
import com.itescia.itbadge.repository.UtilisateurRepository;
import com.itescia.itbadge.service.MailService;
import com.itescia.itbadge.service.UploadService;
import com.itescia.itbadge.service.UserService;
import com.itescia.itbadge.service.UtilisateurService;
import com.itescia.itbadge.service.dto.UserDTO;
import com.itescia.itbadge.service.impl.UtilisateurServiceImpl;
import com.itescia.itbadge.service.util.RandomUtil;
import com.itescia.itbadge.web.rest.UserResource;
import com.itescia.itbadge.web.rest.UtilisateurResource;


@Component
@Primary
public class CustomAuthenticationManager implements AuthenticationManager {
	

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private GroupeRepository groupeRepository;

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
                		
                		UtilisateurService uS = new UtilisateurServiceImpl(utilisateurRepository, coursRepository, groupeRepository);
    		            Utilisateur util = new Utilisateur();
    		            User user = new User();
    		            
    		            
    		            user.setLastName(authentication.getName());
    		            user.setFirstName(authentication.getName());
    		            user.setLogin(authentication.getName());
    		            user.setEmail(authentication.getName() + "@edu.itescia.fr");
    		            
    		            //Production setting
//    		            user.setLastName(userSearch.searchForUser(authentication.getName()).getAttributes().get("nom").get().toString());
//    		            user.setFirstName(userSearch.searchForUser(authentication.getName()).getAttributes().get("prenom").get().toString());
//    		            user.setLogin(userSearch.searchForUser(authentication.getName()).getAttributes().get("uid").get().toString());
//    		            user.setEmail(userSearch.searchForUser(authentication.getName()).getAttributes().get("mail").get().toString());
    		            user.setActivated(true);
    		            String encryptedPassword = new BCryptPasswordEncoder().encode(RandomUtil.generatePassword());;
    		            user.setPassword(encryptedPassword);
    		            Set<Authority> gA = new HashSet<Authority>();
    		            Authority a = new Authority();
    		            a.setName("ROLE_USER");
    		            gA.add(a);
                		user.setAuthorities(gA);					
    		            util.setUser(user);
    		            
    		            if (userSearch.searchForUser(authentication.getName()).getDn().toString().contains("ou=admins"))
    		            {
    		            	util.setIsAdmin(true);
        		            util.setIsProfesseur(false);
    		            }
    		            else if (userSearch.searchForUser(authentication.getName()).getDn().toString().contains("ou=professeurs"))
    		            {
    		            	util.setIsAdmin(false);
        		            util.setIsProfesseur(true);
    		            }
    		            else
    		            {
    		            	util.setIsAdmin(false);
        		            util.setIsProfesseur(false);
    		            }
    		            
    		            
    		            uS.save(util);
    		            isUser = userRepository.findOneByLogin(username);
    		            
    					} catch (Exception e) {
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
