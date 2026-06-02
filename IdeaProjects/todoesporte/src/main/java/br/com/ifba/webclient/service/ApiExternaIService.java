package br.com.ifba.webclient.service;


import br.com.ifba.webclient.dto.PostApiDto;

import java.util.List;

public interface ApiExternaIService {

    //Método para buscar os posts na Api externa
    public List<PostApiDto> buscarPosts();

}
