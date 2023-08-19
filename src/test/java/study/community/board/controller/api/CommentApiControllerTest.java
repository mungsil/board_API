package study.community.board.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Objects;

import static io.jsonwebtoken.Jwts.header;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*
MockMVC: 서버에 애플리케이션을 띄우지 않고도 mvc 패턴으로 동작을 테스트할 수 있는 클래스
(mvc 형태의 mock)
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebClient와 헷갈리지 말기
@AutoConfigureWebTestClient
public class CommentApiControllerTest {

    /*@Test
    void findAllPostTest(@Autowired WebTestClient webTestClient) {
        PostApiController.Result result = Objects.requireNonNull(webTestClient.get()
                .uri("/posts")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJrZmxzZEAxMjMiLCJpYXQiOjE2OTI0MjQ1NDcsImV4cCI6MTY5MjQyODE0N30.J2yhAdVu8pavSuE5zuqZ9aN0hGXpO25refLv_ENCAcs")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostApiController.Result.class)
                .returnResult().getResponseBody());
        System.out.println(result);
    }*/

    @Test
    void changeCommentTest(@Autowired WebTestClient webTestClient) {
        CommentApiController.Result result = Objects.requireNonNull(webTestClient.post()
                .uri("/comments/7")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJrZmxzZEAxMjMiLCJpYXQiOjE2OTI0Mjg4NjEsImV4cCI6MTY5MjQzMjQ2MX0.Pb05WIOaPLwBkbpOewr7JbiuYLcAKVNGQeAGVj4I9cM")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"content\":\"또물보라를일으켜\"}")
                /*.bodyValue("{\"postId\":\"1\", +
                        \"content\":\"또물보라를일으켜\"}")*/
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentApiController.Result.class)
                .returnResult().getResponseBody());

        System.out.println(result);

    }
}