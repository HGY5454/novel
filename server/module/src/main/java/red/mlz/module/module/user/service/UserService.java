package red.mlz.module.module.user.service;

import lombok.extern.slf4j.Slf4j;
import red.mlz.module.module.user.entity.User;
import red.mlz.module.module.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.DataUtils;
import red.mlz.module.utils.SignUtils;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;

@Service
@Slf4j
public class UserService {
    @Resource
    private UserMapper userMapper;

    private static final String DEFAULT_CHARSET = "utf-8";

    private static String ALGORITHM = "AES";

    private static final String CipherMode = "AES/ECB/PKCS5Padding";

    public String decrypt(String sSrc) {
        String secretKey = "123456789";
        try {
            byte[] raw = secretKey.getBytes(DEFAULT_CHARSET);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // 先用base64解密
            byte[] encryptedArr = Base64.getDecoder().decode(sSrc);
            byte[] original = cipher.doFinal(encryptedArr);
            return new String(original, DEFAULT_CHARSET);
        } catch (Exception ex) {
            return "需要加密的秘钥为空";
        }
    }

    public String encrypt(String sSrc) {
        String secretKey = "123456789";
        try {
            byte[] raw = secretKey.getBytes(DEFAULT_CHARSET);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(DEFAULT_CHARSET));

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "AES加密失败";
        }
    }

    public User getUserById(BigInteger id) {
        return userMapper.getUserById(id);
    }

    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    public User getById(BigInteger id) {
        return userMapper.extractById(id);
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int update(User user) {

        return userMapper.update(user);
    }

    public int delete(BigInteger id) {
        return userMapper.delete(id,(int) (System.currentTimeMillis() / 1000));
    }

    public User extractByPhone(String phone) {
        return userMapper.extractByPhone(phone);
    }

    public void refreshUserLoginContext(BigInteger id, int time) {
        User user = new User();
        user.setId(id);
        user.setUpdateTime(time);
        try {
            update(user);
        }
        catch (Exception exception) {
        }
    }

    public BigInteger registerUser(String nickName,String phone,String password
                                   ){
        User newUser = new User();
        int now = BaseUtils.currentSeconds();
        newUser.setNickName(nickName);
        newUser.setPhone(phone);
        newUser.setPassword(SignUtils.marshal(password));
        newUser.setCreateTime(now);
        newUser.setIsDeleted(0);
        insert(newUser);

        return newUser.getId();
    }

    public boolean login(String phone, String password,
                         boolean noPasswd, boolean remember, int lifeTime) {
        if (lifeTime < 0) {
            return false;
        }
        //check phone
        if (!DataUtils.isPhoneNumber(phone)) {
            return false;
        }
        User user = getUserByPhone(phone);
        if (BaseUtils.isEmpty(user)) {
            return false;
        }

        if (noPasswd ||
                password.equals(user.getPassword())) {
            //write session
            //lifeTime = remember ? lifeTime : 0;
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String phone, String password) {
        return login(phone ,password, false, true, SignUtils.getExpirationTime());
    }
}

