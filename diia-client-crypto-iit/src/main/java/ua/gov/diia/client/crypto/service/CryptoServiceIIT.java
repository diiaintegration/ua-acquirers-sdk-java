package ua.gov.diia.client.crypto.service;

import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import ua.gov.diia.client.crypto.api.CryptoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Comparator;

public class CryptoServiceIIT implements CryptoService, AutoCloseable {
    private static final String PATH_NAME_TEMP_DIRECTORY = "iit_crypto";
    private static final String FILE_NAME_SIGNED = "signed.data";
    private static final String FILE_NAME_DESIGNED = "designed.data";

    private EndUserContext context;
    private EndUserPrivateKeyContext privateKeyContext;
    private EndUser endUser;

    private final String privateKeyStorage;
    private final String privateKeyFile;
    private final String privateKeyPassword;
    private final String certificatesStoragePath;
    private final String privateKeyBase64;

    public CryptoServiceIIT(String privateKeyStorage, String privateKeyFile, String privateKeyPassword, String certificatesStoragePath, String privateKeyBase64) {
        this.privateKeyStorage = privateKeyStorage;
        this.privateKeyFile = privateKeyFile;
        this.privateKeyPassword = privateKeyPassword;
        this.certificatesStoragePath = certificatesStoragePath;
        this.privateKeyBase64 = privateKeyBase64;

        init();
    }

    @Override
    public void close() {
        if (endUser != null) {
            try {
                if (context != null)
                    endUser.CtxFree(context);
                if (privateKeyContext != null)
                    endUser.CtxFreePrivateKey(privateKeyContext);
            } catch (Exception ignore) {
            }
        }
    }

    private byte[] decrypt(String data) {
        try {
            return endUser.CtxDevelop(privateKeyContext, data, null, false);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private byte[] deSigned(byte[] data) {
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory(PATH_NAME_TEMP_DIRECTORY);
            Path signFileName = tempDir.resolve(FILE_NAME_SIGNED);
            Files.write(signFileName, data);
            Path designFileName = tempDir.resolve(FILE_NAME_DESIGNED);
            endUser.VerifyFileWithInternalSign(signFileName.toString(), designFileName.toString(), false);
            return Files.readAllBytes(designFileName);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            deleteTempFiles(tempDir);
        }
    }

    @Override
    public byte[] unwrap(String data){
        byte[] decryptData = decrypt(data);
        return deSigned(decryptData);
    }

    @Override
    public String getHash(byte[] data) {
        try {
            return endUser.Hash(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteTempFiles(Path tempDir) {
        if (tempDir != null) {
            try {
                Files.walk(tempDir)
                        .sorted(Comparator.reverseOrder())
                        .forEach(file -> {
                            try {
                                Files.deleteIfExists(file);
                            } catch (IOException e) {
                                //ignore
                            }
                        });
                Files.deleteIfExists(tempDir);
            } catch (IOException e) {
                //ignore
            }
        }
    }

    private void init() {
        try {
            endUser = new EndUser();

            endUser.SetUIMode(false);
            endUser.SetLanguage(EndUser.EU_EN_LANG);
            endUser.Initialize();
            endUser.SetCharset("UTF-8");
            endUser.SetUIMode(false);

            setSettings();

            context = endUser.CtxCreate();
            if ("file".equalsIgnoreCase(privateKeyStorage)) {
                String absolutePathPrivateKey = Paths.get(privateKeyFile).toAbsolutePath().toString();
                privateKeyContext = endUser.CtxReadPrivateKeyFile(context, absolutePathPrivateKey, privateKeyPassword);
            } else if ("binary".equalsIgnoreCase(privateKeyStorage)) {
                byte[] privateKeyBinary = Base64.getDecoder().decode(privateKeyBase64);
                privateKeyContext = endUser.CtxReadPrivateKeyBinary(context, privateKeyBinary, privateKeyPassword);
            } else {
                throw new RuntimeException("Unknown key storage");
            }
        } catch (Exception e) {
            throw new RuntimeException("Signature service isn't initialized", e);
        }
    }

    private void setSettings() throws Exception {
        endUser.SetRuntimeParameter(EndUser.EU_SAVE_SETTINGS_PARAMETER, EndUser.EU_SETTINGS_ID_NONE);

        EndUserModeSettings modeSettings = endUser.CreateModeSettings();
        modeSettings.SetOfflineMode(true);
        endUser.SetModeSettings(modeSettings);

        EndUserFileStoreSettings fileStoreSettings = endUser.CreateFileStoreSettings();
        String absoluteCertsPath = Paths.get(certificatesStoragePath).toAbsolutePath().toString();
        fileStoreSettings.SetPath(absoluteCertsPath);
        endUser.SetFileStoreSettings(fileStoreSettings);

        EndUserProxySettings proxySettings = endUser.CreateProxySettings();
        endUser.SetProxySettings(proxySettings);

        EndUserOCSPSettings ocspSettings = endUser.CreateOCSPSettings();
        ocspSettings.SetUseOCSP(true);
        ocspSettings.SetBeforeStore(true);
        ocspSettings.SetPort("80");
        endUser.SetOCSPSettings(ocspSettings);

        EndUserOCSPAccessInfoModeSettings ocspInfoModeSettings =
                endUser.CreateOCSPAccessInfoModeSettings();
        ocspInfoModeSettings.SetEnabled(true);
        endUser.SetOCSPAccessInfoModeSettings(ocspInfoModeSettings);

        EndUserTSPSettings tspSettings = endUser.CreateTSPSettings();
        endUser.SetTSPSettings(tspSettings);

        EndUserLDAPSettings ldapSettings = endUser.CreateLDAPSettings();
        endUser.SetLDAPSettings(ldapSettings);

        EndUserCMPSettings cmpSettings = endUser.CreateCMPSettings();
        endUser.SetCMPSettings(cmpSettings);
    }
}
