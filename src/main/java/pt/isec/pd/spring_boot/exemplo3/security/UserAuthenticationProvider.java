package pt.isec.pd.spring_boot.exemplo3.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pt.isec.pd.spring_boot.exemplo3.models.User;
import pt.isec.pd.spring_boot.manageDB.DbOperations;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider
{
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        DbOperations db = DbOperations.getInstance();

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        String[] userData = db.getUserData(username);
        User user = new User(userData[0],userData[1]);

        if (db.isAdmin(user.getEmail())) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else if(!db.isAdmin(user.getEmail())){
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
