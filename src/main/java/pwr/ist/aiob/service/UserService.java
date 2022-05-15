package pwr.ist.aiob.service;

import com.google.common.hash.Hashing;
import dev.samstevens.totp.exceptions.QrGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pwr.ist.aiob.mfa.MFATokenManager;
import pwr.ist.aiob.mfa.MfaTokenData;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.repository.UserRepository;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Service("userService")
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private MFATokenManager mfaTokenManager;

    public String registerWithSecret(User user) {
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        hashPassword(user, userEntity);
        String secret = mfaTokenManager.generateSecretKey();
        userEntity.setMfaSecret(secret);
        userEntity.setHas2FA(true);
        userRepository.save(userEntity);
        logger.info("User registered " + userEntity);
        return secret;
    }

    public MfaTokenData mfaSetup(Long id) throws QrGenerationException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return new MfaTokenData(mfaTokenManager.getQRCode(user.get().getMfaSecret()), user.get().getMfaSecret());
        }
        return null;
    }

    public boolean is2FACodeCorrect(String code, String mfaSecret) {
        return mfaTokenManager.verifyTotp(code, mfaSecret);
    }

    public boolean ifUsernameUnique(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        return user.isEmpty();
    }

    public User validateCredentials(User user) {
        Optional<User> dbUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if(dbUser.isPresent()) {
            String pass = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString(); // TODO Powinno być hashowane po stronie klienta
            if (Objects.equals(dbUser.get().getPassword(), pass))
                return dbUser.get();
        }
        return null;
    }

    private void hashPassword(User from, User to) {
        to.setPassword(
                Hashing.sha256().hashString(from.getPassword(), StandardCharsets.UTF_8).toString()
        );
    }
}
