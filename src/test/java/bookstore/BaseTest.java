package bookstore;

import bookstore.clients.BookApiClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class BaseTest {

    protected WireMockServer wireMockServer;
    protected BookApiClient bookApiClient;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WireMockConfiguration config = wireMockConfig()
                .port(8089)
                .usingFilesUnderClasspath("wiremock");

        wireMockServer = new WireMockServer(config);
        wireMockServer.start();
        System.out.println("WireMock сервер запущен: " + wireMockServer.port());

        RestAssured.baseURI = wireMockServer.baseUrl();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        bookApiClient = new BookApiClient();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("WireMock сервер остановлен");
        }
    }
}
