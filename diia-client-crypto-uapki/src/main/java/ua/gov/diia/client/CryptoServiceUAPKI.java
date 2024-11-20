package ua.gov.diia.client;

import com.ftband.uapki.UapkiWrapper;
import ua.gov.diia.client.crypto.api.CryptoService;

import java.util.Base64;

public class CryptoServiceUAPKI implements CryptoService {
    private final UapkiWrapper uapkiWrapper;

    public CryptoServiceUAPKI(String configPath) {
        try {
            uapkiWrapper = new UapkiWrapper();
            uapkiWrapper.init(configPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] unwrap(String data) {
        try {
            return Base64.getDecoder().decode(uapkiWrapper.unwrap(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getHash(byte[] data) {
        try {
            String base64Data = Base64.getEncoder().encodeToString(data);
            return uapkiWrapper.hashData(base64Data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}