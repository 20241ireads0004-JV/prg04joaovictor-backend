package br.com.ifba.infrastructure.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapperUtil {

    // Instância única do ModelMapper
    private static final ModelMapper modelMapper = new ModelMapper();

    // Bloco estático executado assim que a classe é carregada na memória
    static {
        // Define a estratégia de correspondência como STRICT (Estrita).
        // Isso evita ambiguidades com campos que terminam em "Id" (ex: localId e grupoId).
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapeia um objeto de origem para uma nova instância da classe de destino.
     *
     * @param <S> Class Type da Origem (Source)
     * @param <D> Class Type do Destino (Destination)
     * @param source Objeto de origem que será convertido
     * @param destinationClass Classe do objeto de destino
     * @return Nova instância da classe de destino preenchida
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, destinationClass);
    }

    /**
     * Mapeia uma lista de objetos de origem para uma nova lista com a classe de destino.
     *
     * @param <S> Class Type da Origem (Source)
     * @param <D> Class Type do Destino (Destination)
     * @param sourceList Lista de objetos de origem
     * @param destinationClass Classe dos objetos da lista de destino
     * @return Lista com os objetos convertidos
     */
    public static <S, D> List<D> mapAll(List<S> sourceList, Class<D> destinationClass) {
        if (sourceList == null) {
            return List.of();
        }
        return sourceList.stream()
                .map(element -> modelMapper.map(element, destinationClass))
                .collect(Collectors.toList());
    }
}