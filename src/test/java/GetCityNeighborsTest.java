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

public class GetCityNeighborsTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_shouldReturnCityGeorgetown() throws IOException {
        logger.info("Тест get_shouldReturnCityGeorgetown запущен");
        ObjectMapper mapper = new ObjectMapper();
        City city = new City();
        city.setLocalizedName("Georgetown");

        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/35");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/35"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(city))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/neighbors/35");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/35")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Georgetown", mapper.readValue(response.getEntity().getContent(), City.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnCountryUnitedStates() throws IOException {
        logger.info("Тест get_shouldReturnCountryUnitedStates запущен");
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country();
        country.setLocalizedName("United States");

        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/35");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/35"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(country))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/neighbors/35");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/35")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("United States", mapper.readValue(response.getEntity().getContent(), Country.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnAreaGeorgia() throws IOException {
        logger.info("Тест get_shouldReturnAreaGeorgia запущен");
        ObjectMapper mapper = new ObjectMapper();
        AdministrativeArea area = new AdministrativeArea();
        area.setLocalizedName("Georgia");

        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/35");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/35"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(area))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/neighbors/35");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/35")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Georgia", mapper.readValue(response.getEntity().getContent(), AdministrativeArea.class).getLocalizedName());
    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Тест get_shouldReturn500 запущен");
        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/35");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/35"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/neighbors/35");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/35")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}
