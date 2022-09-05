package com.github.vincemann.posting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.vincemann.posting.controller.PostingController;
import com.github.vincemann.posting.dto.CreatePostingDto;
import com.github.vincemann.posting.model.Posting;
import com.github.vincemann.posting.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static com.github.vincemann.springrapid.core.slicing.RapidProfiles.SERVICE;
import static com.github.vincemann.springrapid.core.slicing.RapidProfiles.WEB;
import static com.github.vincemann.springrapid.coretest.slicing.RapidTestProfiles.*;

@Slf4j
@Testcontainers
@ActiveProfiles(value = {SERVICE_TEST,SERVICE,WEB_TEST,WEB,TEST})
@EnableProjectComponentScan
//@SpringBootTest
public class PostingCommentPlatformTest {

    @Autowired
    CommentService commentService;

    @Autowired
    PostingController postingController;

//    @Autowired
//    JsonMapper jsonMapper;
    ObjectMapper objectMapper;


    Posting testPosting;


    @Container
    private DockerComposeContainer composeEnv = new DockerComposeContainer(
            new File("src/test/resources/docker-compose-posting-test.yml")
    );


    private String host;
    private int port;
    private boolean init = false;

    @BeforeEach
    void setup() {
//        this.jsonMapper = new RapidJsonMapper();
        this.objectMapper = new JsonMapper();
        if (!init){
//            composeEnv.withLogConsumer("eurekaserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));

            composeEnv.withExposedService("database", 5532);
            composeEnv.withExposedService("configserver", 8071);
//                    .waitingFor("database",Wait.forListeningPort());

//
//
////        String configServerHost = composeEnv.getServiceHost("posting-service", 8080);
////        Integer configServerPort = composeEnv.getServicePort("posting-service", 8080);
////        String healthCheckUrl = configServerHost+":"+configServerPort+"/actuator/health";
//
            composeEnv.withExposedService("eurekaserver", 8070);
//                    .waitingFor("configserver",Wait.forHttp("/actuator/health"));
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.withExposedService("posting-service", 8080);
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.withExposedService("comment-service", 8080);
//                    .waitingFor("configserver",Wait.forListeningPort());

            composeEnv.withLogConsumer("database", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("database", Wait.forLogMessage(".*database system is ready to accept connections.*", 1)/*.withStartupTimeout(Duration.ofSeconds(60))*/);

            composeEnv.withLogConsumer("configserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("configserver", Wait.forLogMessage(".*Started ConfigurationServerApplication.*", 1)/*.withStartupTimeout(Duration.ofSeconds(60))*/);

            composeEnv.withLogConsumer("eurekaserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("eurekaserver", Wait.forLogMessage(".*Started EurekaServerApplication.*", 1)/*.withStartupTimeout(Duration.ofSeconds(60))*/);

            composeEnv.withLogConsumer("posting-service", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("posting-service", Wait.forLogMessage(".*Started PostingServiceApplication.*", 1)/*.withStartupTimeout(Duration.ofSeconds(60))*/);

            composeEnv.withLogConsumer("comment-service", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("comment-service", Wait.forLogMessage(".*Started CommentServiceApplication.*", 1)/*.withStartupTimeout(Duration.ofSeconds(60))*/);



            composeEnv.start();

            host = composeEnv.getServiceHost("posting-service", 8080);
            port = composeEnv.getServicePort("posting-service", 8080);


//            postingServiceHost = "localhost";
//            postingServicePort = 8080;
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
//                new File("src/test/resources/docker-compose-posting-test.yml"));
//        compose.withExposedService("gateway", 8082);
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
        host = composeEnv.getServiceHost("posting-service",8080);
        port = composeEnv.getServicePort("posting-service",8080);

        String endpoint = "http://"
                + host + ":"
                + port
                + postingController.getCreateUrl();
//                + "/api/core/posting/create";


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endpoint);

        CreatePostingDto createPostingDto = new CreatePostingDto(testPosting);
        String json = objectMapper.writeValueAsString(createPostingDto);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        Assertions.
                assertEquals(response.getStatusLine().getStatusCode(), 200);
        client.close();
    }

    @Test
    public void checkConfigServerHealth()
            throws Exception {
        host = composeEnv.getServiceHost("configserver",8071);
        port = composeEnv.getServicePort("configserver",8071);

        String endpoint = "http://"
                + host + ":"
                + port
//                + postingController.getCreateUrl();
                + "/actuator/health";


        CloseableHttpClient client = HttpClients.createDefault();

//        HttpPost httpPost = new HttpPost(endpoint);
        HttpGet httpGet = new HttpGet(endpoint);
        httpGet.setHeader("Accept", "application/json");
//        httpGet.setHeader("Content-type", "application/json");


        CloseableHttpResponse response = client.execute(httpGet);
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.err.println(responseBody);
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