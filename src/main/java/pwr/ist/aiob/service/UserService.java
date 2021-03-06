package pwr.ist.aiob.service;

import com.google.common.hash.Hashing;
import dev.samstevens.totp.exceptions.QrGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pwr.ist.aiob.security.mfa.MFATokenManager;
import pwr.ist.aiob.security.mfa.MfaTokenData;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MfaTokenData registerWithSecret(User user) throws QrGenerationException {
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        //hashPassword(user, userEntity);
        String secret = mfaTokenManager.generateSecretKey();
        String qrCode = mfaTokenManager.getQRCode(secret);
        userEntity.setMfaSecret(secret);
        userEntity.setHas2FA(true);
        userRepository.save(userEntity);
        logger.info("User registered " + userEntity);
        return new MfaTokenData(qrCode, secret);
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

    private void hashPassword(User from, User to) {
        to.setPassword(
                Hashing.sha256().hashString(from.getPassword(), StandardCharsets.UTF_8).toString()
        );
    }
}
