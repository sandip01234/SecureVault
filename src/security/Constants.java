package security;

public class Constants {

    public static final String AES_KEY = System.getenv("VAULT_AES_KEY")!=null ? System.getenv("VAULT_AES_KEY") : "default_aes_key_123";
}
