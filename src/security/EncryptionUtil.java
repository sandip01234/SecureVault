package security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class EncryptionUtil {
    private static byte[] getKeyBytes(String key) {
        byte[] k = key.getBytes();
        return Arrays.copyOf(k, 16); // pad/truncate to 16 bytes for AES-128
    }

    public static void encrypt(Path input, Path output) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKeyBytes(Constants.AES_KEY), "AES"));
        Files.write(output, cipher.doFinal(Files.readAllBytes(input)));
    }

    public static void decrypt(Path input, Path output) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKeyBytes(Constants.AES_KEY), "AES"));
        Files.write(output, cipher.doFinal(Files.readAllBytes(input)));
    }
}
