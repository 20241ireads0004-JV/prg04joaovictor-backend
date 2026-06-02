package br.com.ifba.webclient.controller;

import br.com.ifba.webclient.dto.PostApiDto;
import br.com.ifba.webclient.service.ApiExternaIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-externa")
@RequiredArgsConstructor
public class ApiExternaController {

    private final ApiExternaIService apiExternaService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostApiDto>> buscarPosts() {

        return ResponseEntity.ok(
                apiExternaService.buscarPosts()
        );
    }
}
