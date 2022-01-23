package io.github._7isenko.SCP1985.security;

import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.repositories.AccessKeyEntityRepository;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author 7isenko
 */
@Service("personnelDetailsService")
@Transactional
public class PersonnelDetailsService implements UserDetailsService {

    private final PersonnelEntityRepository personnelEntityRepository;

    public PersonnelDetailsService(PersonnelEntityRepository personnelEntityRepository) {
        this.personnelEntityRepository = personnelEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("admin")) {
            return new User("admin", "admin",
                    Collections.singletonList(new SimpleGrantedAuthority("admin")));
        }

        PersonnelEntity personnel = personnelEntityRepository.findPersonnelEntityByFullName(username);
        if (personnel == null) throw new UsernameNotFoundException(String.format("Personnel %s not found.", username));

        return new User(username, personnel.getAccessKeyById().getSshKey(),
                Collections.singletonList(new SimpleGrantedAuthority(personnel.getClassification().name())));
    }

}
