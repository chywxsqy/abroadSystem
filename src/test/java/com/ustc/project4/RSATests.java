package com.ustc.project4;


import com.ustc.project4.util.RSAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class RSATests {

    @Value("${rsa.privatekey}")
    private String privateKey;

    @Value("${rsa.publickey}")
    private String publicKey;

    @Test
    public void initKeytest() {
        try {
            Map<String, Key> keyMap= RSAUtil.initKey();
            System.out.println(RSAUtil.getPrivateKey(keyMap));
            System.out.println(RSAUtil.getPublicKey(keyMap));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptionAndDecryptionTest() throws Exception {
        Map<String, Key> keyMap = null;
        try {
            keyMap= RSAUtil.initKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] bytes = RSAUtil.encryptByPublicKey("123123", RSAUtil.getPublicKey(keyMap));
        System.out.println(new String(bytes));
        byte[] data = RSAUtil.decryptByPrivateKey(bytes, RSAUtil.getPrivateKey(keyMap));
        System.out.println(new String(data));
    }

    @Test
    public void test() throws Exception {
        byte[] bytes = RSAUtil.encryptByPublicKey("123123", publicKey);
        String s = RSAUtil.encryptBASE64(bytes);

        System.out.println(s);

    }
}
