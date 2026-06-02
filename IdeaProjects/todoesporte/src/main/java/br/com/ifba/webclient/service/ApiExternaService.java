package br.com.ifba.webclient.service;

import br.com.ifba.webclient.dto.PostApiDto;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApiExternaService implements ApiExternaIService{

    private final WebClient webClient;
    private final Validator validator;

    @Override
    public List<PostApiDto> buscarPosts() {

        List<PostApiDto> posts = webClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .retrieve()
                .bodyToFlux(PostApiDto.class)
                .collectList()
                .block();

        posts.forEach(post -> {
            Set<ConstraintViolation<PostApiDto>> violations =
                    validator.validate(post);

            if (!violations.isEmpty()) {
                throw new IllegalArgumentException(
                        "Dados inválidos recebidos da API externa."
                );
            }
        });

        return posts;
    }
}
