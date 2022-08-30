package com.github.vincemann.posting;

import com.github.vincemann.posting.controller.PostingController;
import com.github.vincemann.posting.dto.CreatePostingDto;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.service.CommentService;
import com.github.vincemann.springrapid.core.controller.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Slf4j
@Testcontainers
public class PostingCommentPlatformTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    PostingController postingController;

    @Autowired
    JsonMapper jsonMapper;


    Posting testPosting;


    @Container
    private DockerComposeContainer composeEnv = new DockerComposeContainer(
            new File("src/test/resources/test-compose.yml")
    );


    private String postingServiceHost;
    private int postingServicePort;
    private boolean init = false;

    @BeforeEach
    void setup() {
        if (!init){
            composeEnv.withLogConsumer("eurekaserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));

            composeEnv.withExposedService("database", 5532);
            composeEnv.withExposedService("configserver", 8071);
//                    .waitingFor("database",Wait.forListeningPort());



//        String configServerHost = composeEnv.getServiceHost("posting-service", 8080);
//        Integer configServerPort = composeEnv.getServicePort("posting-service", 8080);
//        String healthCheckUrl = configServerHost+":"+configServerPort+"/actuator/health";

            composeEnv.withExposedService("eurekaserver", 8070);
//                    .waitingFor("configserver",Wait.forHttp("/actuator/health"));
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.withExposedService("posting-service", 8080);
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.withExposedService("comment-service", 8080);
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.start();

            postingServiceHost = composeEnv.getServiceHost("posting-service", 8080);
            postingServicePort = composeEnv.getServicePort("posting-service", 8080);
            init=true;
        }


        testPosting = Posting.builder()
                .contactable(true)
                .latitude(123L)
                .longitude(145L)
                .text("nice beach dude")
                .title("beach")
                .build();
    }

    @AfterEach
    void tearDown() {
        composeEnv.stop();
    }

    //    static {
//
//
//        compose = new DockerComposeContainer<>(
//                new File("src/test/resources/test-compose.yml"));
//        compose.withExposedService("gateway", 8082);
//        compose.withLogConsumer("gateway", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
//        compose.withLogConsumer("calculator", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
//        compose.withLogConsumer("main", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
//        compose.waitingFor("gateway", Wait.forLogMessage(".*Started GatewayApplication.*", 1));
//        compose.waitingFor("calculator", Wait.forLogMessage(".*Started CalculatorApplication.*", 1));
//        compose.waitingFor("main", Wait.forLogMessage(".*Started KompSysApplication.*", 1));
////        this is key; increase var 'times' when adding containers
//        compose.waitingFor("rabbitmq", Wait.forLogMessage(".*granted access.*", 2));
//    }

    @Test
    public void createPosting()
            throws Exception {
        String endpoint = "http://"
                + postingServiceHost + ":"
                + postingServicePort
                + postingController.getCreateUrl();


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endpoint);

        CreatePostingDto createPostingDto = new CreatePostingDto(testPosting);
        String json = jsonMapper.writeDto(createPostingDto);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        client.close();
    }

//    @Test
//    public void createCommentsForPosting() throws Exception {
//        String address = "http://"
//                + compose.getServiceHost("gateway", 8082) + ":"
//                + compose.getServicePort("gateway", 8082)
//                + "/api/v1/car?id=3";
//        String response = simpleGetRequest(address);
//        System.out.println("MYRESPONSE: " + response);
//        String correctResponse = "{\"id\":3,\"name\":\"plymouth satellite\",\"price\":40000,\"milesPerGallon\":18,\"cylinders\":8,\"displacement\":318,\"horsepower\":150,\"weightInPounds\":3436,\"acceleration\":11,\"year\":\"1970-01-01\",\"origin\":\"USA\"}";
//        Assertions.assertEquals(correctResponse, response);
//    }
//
//    @Test
//    public void findPostingWithAllComments() throws JSONException {
//        long id = 1;
//        String address = "http://"
//                + compose.getServiceHost("gateway", 8082) + ":"
//                + compose.getServicePort("gateway", 8082);
//        WebClient webClient = WebClient.create(address);
//
//        CarView carView = webClient.method(HttpMethod.DELETE)
//                .uri("/api/v1/car/")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(BodyInserters.fromProducer(Mono.just(new JSONObject().put("id", "1").toString()), String.class))
//                .retrieve()
//                .bodyToMono(CarView.class)
//                .block();
//
//        Assertions.assertEquals(id, carView.getId());
//    }
//
//    private String simpleGetRequest(String address) throws Exception {
//        URL url = new URL(address);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer content = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();
//
//        return content.toString();
//    }
}