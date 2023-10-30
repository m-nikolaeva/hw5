import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetFiveDaysOfWeatherAlarmsTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_shouldReturn200() throws IOException {
        logger.info("Тест get_shouldReturn200 запущен");

        logger.debug("Формируем мок GET /alarms/v1/5day/46");
        stubFor(get(urlPathEqualTo("/alarms/v1/5day/46"))
                .willReturn(aResponse().withStatus(200)));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/alarms/v1/5day/46");
        HttpResponse responseOk = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/alarms/v1/5day/46")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Тест get_shouldReturn500 запущен");
        logger.debug("Формируем мок GET /alarms/v1/7day/46");
        stubFor(get(urlPathEqualTo("/alarms/v1/7day/46"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/alarms/v1/7day/46");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/alarms/v1/7day/46")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}
