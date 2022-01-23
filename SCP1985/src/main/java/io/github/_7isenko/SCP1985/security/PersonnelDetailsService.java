package io.github._7isenko.SCP1985.security;

import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.object_types.ClearanceLevel;
import io.github._7isenko.SCP1985.model.repositories.PersonnelEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

        PersonnelEntity personnel = personnelEntityRepository.findPersonnelEntityByFullName(username);
        if (personnel == null) throw new UsernameNotFoundException(String.format("Personnel %s not found.", username));

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        if (personnel.getClearanceLevel() == ClearanceLevel.SIX) {
            authorities.add(new SimpleGrantedAuthority("admin"));
        }
        authorities.add(new SimpleGrantedAuthority(personnel.getClassification().name()));

        return new User(username, personnel.getAccessKeyById().getSshKey(),
                authorities);
    }

}
