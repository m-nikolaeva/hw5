import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public abstract class AbstractTest {

    private static WireMockServer wireMockServer = new WireMockServer();
    private static final int port = 8080;
    private static String baseUrl;
    private static final Logger logger
            = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeAll
    static void startServer() {
        baseUrl = "http://localhost:" + port;
        wireMockServer.start();
        configureFor("localhost", port);
        logger.info("Start WireMockServer on port {}", port);
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
        logger.info("Stop WireMockServer");
    }

    public String convertResponseToString(HttpResponse response) throws IOException {
        logger.debug("convertResponseToString method call");
        try(InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\Z").next();
        }
    }

    public static String getBaseUrl(){
        return baseUrl;
    }
}
