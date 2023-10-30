import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gb.Location;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAutocompleteTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(GetAutocompleteTest.class);

    @Test
    void get_shouldReturn200() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturn200 запущен");
        ObjectMapper mapper = new ObjectMapper();
        Location autocompleteOk = new Location();
        autocompleteOk.setKey("Ok");

        Location autocompleteErr = new Location();
        autocompleteErr.setKey("Error");

        logger.debug("Формируем мок GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Texas City"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(autocompleteOk))));

        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Error"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(autocompleteErr))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("q", "Texas City")
                .build();
        request.setURI(uriOk);

        HttpResponse responseOk = httpClient.execute(request);

        URI uriErr = new URIBuilder(request.getURI())
                .addParameter("q", "Error")
                .build();
        request.setURI(uriErr);
        HttpResponse responseErr = httpClient.execute(request);

        verify(2, getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, responseOk.getStatusLine().getStatusCode());
        assertEquals(200, responseErr.getStatusLine().getStatusCode());
        assertEquals("Ok", mapper.readValue(responseOk.getEntity().getContent(), Location.class).getKey());
        assertEquals("Error", mapper.readValue(responseErr.getEntity().getContent(), Location.class).getKey());
    }

    @Test
    void get_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест get_shouldReturn401 запущен");
        logger.debug("Формируем мок GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("apiKey", notMatching("eOALDWrCoESQhiaQACXwdOiLPTJucnzP"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("ERROR")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "eOALDWrCoESQhiaQACXwdOiLPTJucnzP_res")
                .build();
        request.setURI(uri);

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));
    }
}
