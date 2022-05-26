package pwr.ist.aiob.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pwr.ist.aiob.model.CustomUserDetails;
import pwr.ist.aiob.security.mfa.MFATokenManager;

import javax.annotation.Resource;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Resource
    private MFATokenManager mfaTokenManager;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        super.additionalAuthenticationChecks(userDetails, authentication);

        CustomWebAuthenticationDetails authenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
        CustomUserDetails user = (CustomUserDetails) userDetails;
        String mfaToken = authenticationDetails.getToken();
        if(!mfaTokenManager.verifyTotp(mfaToken, user.getMfaSecret())){
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }
}
