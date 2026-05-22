package br.com.ifba.infraestructure.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, D> D map(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    public static <S, D> List<D> mapAll(List<S> sourceList, Class<D> destinationClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, destinationClass))
                .collect(Collectors.toList());
    }
}