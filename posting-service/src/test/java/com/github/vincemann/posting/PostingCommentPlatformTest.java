package com.github.vincemann.posting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vincemann.posting.controller.PostingController;
import com.github.vincemann.posting.dto.CreatePostingDto;
import com.github.vincemann.posting.dto.ReadPostingWithCommentsDto;
import com.github.vincemann.posting.model.Comment;
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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.vincemann.ezcompare.Comparator.compare;
import static com.github.vincemann.springrapid.core.slicing.RapidProfiles.SERVICE;
import static com.github.vincemann.springrapid.core.slicing.RapidProfiles.WEB;
import static com.github.vincemann.springrapid.coretest.slicing.RapidTestProfiles.*;

@Slf4j
@Testcontainers
@ActiveProfiles(value = {SERVICE_TEST, SERVICE, WEB_TEST, WEB, TEST})
@EnableProjectComponentScan
//@SpringBootTest
public class PostingCommentPlatformTest {

    @Autowired
    CommentService commentService;

    @Autowired
    PostingController postingController;

    ObjectMapper objectMapper;


    Posting testPosting;

    Comment testComment1;
    Comment testComment2;

    @Container
    private DockerComposeContainer composeEnv = new DockerComposeContainer(
            new File("src/test/resources/docker-compose-posting-test.yml")
    );


    private String postingHost;
    private int postingPort;
    private String commentHost;
    private int commentPort;
    private boolean init = false;

    @BeforeEach
    void setup() {
//        this.jsonMapper = new RapidJsonMapper();
        this.objectMapper = new ObjectMapper();
        if (!init) {
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
            composeEnv.waitingFor("database", Wait.forLogMessage(".*database system is ready to accept connections.*", 1).withStartupTimeout(Duration.ofSeconds(90)));

            composeEnv.withLogConsumer("configserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("configserver", Wait.forLogMessage(".*Started ConfigurationServerApplication.*", 1).withStartupTimeout(Duration.ofSeconds(90)));

            composeEnv.withLogConsumer("eurekaserver", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("eurekaserver", Wait.forLogMessage(".*Started EurekaServerApplication.*", 1).withStartupTimeout(Duration.ofSeconds(90)));

            composeEnv.withLogConsumer("posting-service", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("posting-service", Wait.forLogMessage(".*Started PostingServiceApplication.*", 1).withStartupTimeout(Duration.ofSeconds(90)));
            composeEnv.waitingFor("posting-service", Wait.forLogMessage(".*Registering application POSTING-SERVICE with eureka with status UP.*", 1).withStartupTimeout(Duration.ofSeconds(90)));


            composeEnv.withLogConsumer("comment-service", new Slf4jLogConsumer(PostingCommentPlatformTest.log));
            composeEnv.waitingFor("comment-service", Wait.forLogMessage(".*Started CommentServiceApplication.*", 1).withStartupTimeout(Duration.ofSeconds(90)));
            composeEnv.waitingFor("comment-service", Wait.forLogMessage(".*Registering application COMMENT-SERVICE with eureka with status UP.*", 1).withStartupTimeout(Duration.ofSeconds(90)));


            composeEnv.start();

            postingHost = composeEnv.getServiceHost("posting-service", 8080);
            postingPort = composeEnv.getServicePort("posting-service", 8080);


            commentHost = composeEnv.getServiceHost("comment-service", 8080);
            commentPort = composeEnv.getServicePort("comment-service", 8080);

//            postingServiceHost = "localhost";
//            postingServicePort = 8080;
            init = true;
        }


        testPosting = Posting.builder()
                .contactable(true)
                .latitude(123L)
                .longitude(145L)
                .text("nice beach dude")
                .title("beach")
                .build();

        testComment1 = Comment.builder()
                .text("example comment 1")
                .build();

        testComment2 = Comment.builder()
                .text("second example comment")
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
    public void canCreatePosting()
            throws Exception {
        createPosting();
    }

    public CreatePostingDto createPosting() throws IOException {
//        postingHost = composeEnv.getServiceHost("posting-service", 8080);
//        postingPort = composeEnv.getServicePort("posting-service", 8080);

        System.err.println("posting:");
        System.err.println(postingHost +":"+ postingPort);


        String endpoint = "http://"
                + postingHost + ":"
                + postingPort
//                + postingController.getCreateUrl();
                + "/api/core/posting/create";


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endpoint);

        CreatePostingDto createPostingDto = new CreatePostingDto(testPosting);
        String json = objectMapper.writeValueAsString(createPostingDto);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        CreatePostingDto readPostingDto = objectMapper.readValue(responseBody, CreatePostingDto.class);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        client.close();
        return readPostingDto;
    }

    @Test
    public void canCreateComment() throws Exception {
        CreatePostingDto posting = createPosting();
        createComment(posting.getId(), "example comment");
    }

    public Comment createComment(Long postingId, String text) throws Exception {


//        postingHost = composeEnv.getServiceHost("comment-service", 8080);
//        postingPort = composeEnv.getServicePort("comment-service", 8080);

        System.err.println("comment:");
        System.err.println(commentHost +":"+ commentPort);

        String endpoint = "http://"
                + commentHost + ":"
                + commentPort
//                + postingController.getCreateUrl();
                + "/api/core/comment/create";


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endpoint);

        Comment createCommentDto = new Comment(postingId, text);
        String json = objectMapper.writeValueAsString(createCommentDto);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Comment readCommentDto = objectMapper.readValue(responseBody, Comment.class);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        client.close();
        return readCommentDto;
    }

    @Test
    public void findPostingWithComments()
            throws Exception {
        CreatePostingDto posting = createPosting();
        Comment firstComment = createComment(posting.getId(), testComment1.getText());
        Comment secondComment = createComment(posting.getId(), testComment2.getText());


//        postingHost = composeEnv.getServiceHost("posting-service", 8080);
//        postingPort = composeEnv.getServicePort("posting-service", 8080);

        System.err.println("posting with comments:");
        System.err.println(postingHost +":"+ postingPort);

        String endpoint = "http://"
                + postingHost + ":"
                + postingPort
                + "/api/core/posting/find-with-comments?posting-id=" + posting.getId().toString();

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(endpoint);
        httpGet.setHeader("Accept", "application/json");


        CloseableHttpResponse response = client.execute(httpGet);
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.err.println(responseBody);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        client.close();

        ReadPostingWithCommentsDto responseDto = objectMapper.readValue(responseBody, ReadPostingWithCommentsDto.class);

        compare(responseDto)
                .with(posting)
                .properties()
                .all()
                .ignore(responseDto::getComments)
                .ignore(testPosting::getCommercialText)
                .assertEqual();

        List<Comment> comments = responseDto.getComments().stream().collect(Collectors.toList());
        Assertions.assertEquals(2,comments.size());

        Comment firstCommentDto = responseDto.getComments().stream().filter(c -> c.getText().equals(firstComment.getText())).findFirst().get();
        Comment secondCommentDto = responseDto.getComments().stream().filter(c -> c.getText().equals(secondComment.getText())).findFirst().get();
        compare(firstCommentDto)
                .with(firstComment)
                .properties().all().assertEqual();

        compare(secondCommentDto)
                .with(secondComment)
                .properties().all().assertEqual();

    }

//    @Test
//    public void checkConfigServerHealth()
//            throws Exception {
//        postingHost = composeEnv.getServiceHost("configserver", 8071);
//        postingPort = composeEnv.getServicePort("configserver", 8071);
//
//        String endpoint = "http://"
//                + postingHost + ":"
//                + postingPort
//                + "/actuator/health";
//
//
//        CloseableHttpClient client = HttpClients.createDefault();
//
////        HttpPost httpPost = new HttpPost(endpoint);
//        HttpGet httpGet = new HttpGet(endpoint);
//        httpGet.setHeader("Accept", "application/json");
////        httpGet.setHeader("Content-type", "application/json");
//
//
//        CloseableHttpResponse response = client.execute(httpGet);
//        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//        System.err.println(responseBody);
//        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
//        client.close();
//    }


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