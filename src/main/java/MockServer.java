import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {

    @ClassRule
    public static final WireMockRule wireMockRule = new WireMockRule();

    public static WireMockConfiguration configuration() {
        //Используем wireMockConfig для добавления extensions, в частности ResponseTemplateTransformer, который нужен для распознавания JSON и пр. в body.
        return WireMockConfiguration.wireMockConfig().extensions(new ResponseTemplateTransformer(true));
        //        return WireMockConfiguration.wireMockConfig().extensions(new ResponseTemplateTransformer(true)).notifier(new ConsoleNotifier(true)); // Тоже самое, но с логгированием.
    }

    public static void createWireMockStub() {
        String fio = "Ivanov Ivan Ivanovich";
        String position = "QA engineer";
        int number = 1024;
        int id = 101;

        // Настройка заглушки для POST-запроса с JSON синтаксисом.
        String key = "Content-Type";
        String contentType = "application/json";
        stubFor(post(urlPathMatching("/mock/create_employee"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader(key, contentType)
                        // {{randomValue type='UUID'}} - для генерации uuid
                        .withBody("{\"fio\": \"{{jsonPath  request.body '$.fio'}}\", \"position\": \"{{jsonPath  request.body '$.position'}}\", \"number\": \"{{randomValue type='UUID'}}\" }")));

        // Настройка заглушки для GET-запроса с JSON синтаксисом.
        stubFor(get(urlPathMatching("/mock/get_employee"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(key, contentType)
                        .withBody("{\"id\": \"" + id + "\" , \"fio\": \"" + fio + "\", \"position\": \"" + position + "\", \"number\": \"" + number + "\" }")));

        // Настройка заглушки для DELETE-запроса с JSON синтаксисом.
        stubFor(delete(urlPathMatching("/mock/delete_employee"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(key, contentType)
                        // {{capitalize request.query.id}} - для распознавания id из Query Params
                        .withBody("{\"id\": \"{{capitalize request.query.id}}\", \"status\": \"DELETED\" }")));


    }

    // Запуск мок-сервера.
    public static void main(String[] args) {
        // Используем конструктор configuration, объявленный ранее.
        WireMockServer wireMockServer = new WireMockServer(configuration());
        wireMockServer.start();
        createWireMockStub();
    }
}
