package main.java.br.com.ifba.br.infraestructure.entity;

@MappedSuperClass
@Data
public class PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
