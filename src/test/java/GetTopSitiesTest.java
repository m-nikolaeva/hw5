import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gb.AdministrativeArea;
import ru.gb.City;
import ru.gb.Country;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTopSitiesTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_TopCitiesContainSantiago() throws IOException {
        logger.info("Тест get_CorrectContentTopCities запущен");
        ObjectMapper mapper = new ObjectMapper();
        City city = new City();
        city.setLocalizedName("Santiago");

        logger.debug("Формируем мок GET /locations/v1/topcities/50");
        stubFor(get(urlPathEqualTo("/locations/v1/topcities/50"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(city))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/topcities/50");

        HttpResponse responseOk = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/topcities/50")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals("Santiago", mapper.readValue(responseOk.getEntity().getContent(), City.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnCountryChile() throws IOException {
        logger.info("Тест get_shouldReturnCountryChile запущен");
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country();
        country.setLocalizedName("Chile");

        logger.debug("Формируем мок GET /locations/v1/topcities/50");
        stubFor(get(urlPathEqualTo("/locations/v1/topcities/50"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(country))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/topcities/50");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/topcities/50")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Chile", mapper.readValue(response.getEntity().getContent(), Country.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnAreaDeSantiago() throws IOException {
        logger.info("Тест get_shouldReturnAreaDeSantiago запущен");
        ObjectMapper mapper = new ObjectMapper();
        AdministrativeArea area = new AdministrativeArea();
        area.setLocalizedName("Región Metropolitana de Santiago");

        logger.debug("Формируем мок GET /locations/v1/topcities/50");
        stubFor(get(urlPathEqualTo("/locations/v1/topcities/50"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(area))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/topcities/50");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/topcities/50")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Región Metropolitana de Santiago", mapper.readValue(response.getEntity().getContent(), AdministrativeArea.class).getLocalizedName());
    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Тест get_shouldReturn500 запущен");
        logger.debug("Формируем мок GET /locations/v1/topcities/50");
        stubFor(get(urlPathEqualTo("/locations/v1/topcities/50"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/topcities/50");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/topcities/50")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}
