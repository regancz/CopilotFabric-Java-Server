package com.charles.copilotfabric.api.configuration;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author charles
 * @date 2024/1/22 20:49
 */

//@Component
public class FabricConfiguration {
    private final Gateway gateway;
//    private final Network network;

    public FabricConfiguration() throws IOException {
        try {
            Properties properties = new Properties();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            String networkConfigPath = properties.getProperty("networkConfigPath");
            String certificatePath = properties.getProperty("certificatePath");
            String privateKeyPath = properties.getProperty("privateKeyPath");
            X509Certificate certificate = readX509Certificate(Paths.get(certificatePath));
            PrivateKey privateKey = getPrivateKey(Paths.get(privateKeyPath));
            Wallet wallet = Wallets.newInMemoryWallet();
            wallet.put("user1", Identities.newX509Identity("Org1MSP",certificate,privateKey));
            Gateway.Builder builder = Gateway.createBuilder()
                    .identity(wallet, "user1")
                    .networkConfig(Paths.get(networkConfigPath));
            gateway = builder.connect();
        } catch (InvalidKeyException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    public static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
