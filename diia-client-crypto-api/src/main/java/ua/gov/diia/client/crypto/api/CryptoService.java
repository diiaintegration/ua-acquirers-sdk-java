package ua.gov.diia.client.crypto.api;

public interface CryptoService {
    byte[] unwrap(String data);
    String getHash(byte[] data);
}
