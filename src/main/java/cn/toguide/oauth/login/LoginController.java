package cn.toguide.oauth.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author yaoyuquan
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @PostMapping("/{code}")
    public Mono<GithubResponse> login(@PathVariable String code) {

        return WebClient.create("https://github.com")
                .post()
                .uri("/login/oauth/access_token?client_id={clientId}&client_secret={clientSecret}&code={code}", clientId, clientSecret, code)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .doOnNext(response -> log.debug("response from github : {} ", response.toString()));

    }


}
