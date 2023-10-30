import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gb.AdministrativeArea;
import ru.gb.City;
import ru.gb.Country;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetGeopositionSearchTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_shouldReturnCityAkqiCounty() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnAkqiCounty запущен");
        ObjectMapper mapper = new ObjectMapper();
        City city = new City();
        city.setLocalizedName("Akqi County");

        logger.debug("Формируем мок GET /locations/v1/cities/geoposition/search");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/geoposition/search"))
                .withQueryParam("q", equalTo("40.79, 77.86"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(city))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/geoposition/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "40.79, 77.86")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/geoposition/search")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Akqi County", mapper.readValue(response.getEntity().getContent(), City.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnCountryChina() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnCountryChina запущен");
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country();
        country.setLocalizedName("China");

        logger.debug("Формируем мок GET /locations/v1/cities/geoposition/search");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/geoposition/search"))
                .withQueryParam("q", equalTo("40.79, 77.86"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(country))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/geoposition/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "40.79, 77.86")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/geoposition/search")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("China", mapper.readValue(response.getEntity().getContent(), Country.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnAreaXinjiang() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnAreaXinjiang запущен");
        ObjectMapper mapper = new ObjectMapper();
        AdministrativeArea area = new AdministrativeArea();
        area.setLocalizedName("Xinjiang");

        logger.debug("Формируем мок GET /locations/v1/cities/geoposition/search");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/geoposition/search"))
                .withQueryParam("q", equalTo("40.79, 77.86"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(area))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/geoposition/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "40.79, 77.86")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/geoposition/search")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Xinjiang", mapper.readValue(response.getEntity().getContent(), AdministrativeArea.class).getLocalizedName());
    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Тест get_shouldReturn500 запущен");
        logger.debug("Формируем мок GET /locations/v1/cities/geoposition/search");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/geoposition/search"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/geoposition/search");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/geoposition/search")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}


