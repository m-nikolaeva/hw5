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

public class GetPostalCodeSearchByCountrycodeTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_shouldReturnYork() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnYork запущен");
        ObjectMapper mapper = new ObjectMapper();
        City city = new City();
        city.setLocalizedName("York");

        logger.debug("Формируем мок GET /locations/v1/postalcodes/GB/search");
        stubFor(get(urlPathEqualTo("/locations/v1/postalcodes/GB/search"))
                .withQueryParam("q", equalTo("YO30 7"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(city))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/postalcodes/GB/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "YO30 7")
                .build();
        request.setURI(uri);

        HttpResponse responseOk = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/postalcodes/GB/search")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals("York", mapper.readValue(responseOk.getEntity().getContent(), City.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnCountryUnitedKingdom() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnCountryUnitedKingdom запущен");
        ObjectMapper mapper = new ObjectMapper();
        Country country = new Country();
        country.setLocalizedName("United Kingdom");

        logger.debug("Формируем мок GET /locations/v1/postalcodes/GB/search");
        stubFor(get(urlPathEqualTo("/locations/v1/postalcodes/GB/search"))
                .withQueryParam("q", equalTo("YO30 7"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(country))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/postalcodes/GB/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "YO30 7")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/postalcodes/GB/search")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("United Kingdom", mapper.readValue(response.getEntity().getContent(), Country.class).getLocalizedName());
    }

    @Test
    void get_shouldReturnAreaYork() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturnAreaYork запущен");
        ObjectMapper mapper = new ObjectMapper();
        AdministrativeArea area = new AdministrativeArea();
        area.setLocalizedName("York");

        logger.debug("Формируем мок GET /locations/v1/postalcodes/GB/search");
        stubFor(get(urlPathEqualTo("/locations/v1/postalcodes/GB/search"))
                .withQueryParam("q", equalTo("YO30 7"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(area))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/postalcodes/GB/search");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "YO30 7")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/postalcodes/GB/search")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("York", mapper.readValue(response.getEntity().getContent(), AdministrativeArea.class).getLocalizedName());
    }

    @Test
    void get_shouldReturn500() throws IOException {
        logger.info("Тест get_shouldReturn500 запущен");
        logger.debug("Формируем мок GET /locations/v1/postalcodes/GB/search");
        stubFor(get(urlPathEqualTo("/locations/v1/postalcodes/GB/search"))
                .willReturn(aResponse()
                        .withStatus(500).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/postalcodes/GB/search");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/postalcodes/GB/search")));
        assertEquals(500, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}
