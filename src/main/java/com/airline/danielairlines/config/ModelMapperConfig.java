package com.airline.danielairlines.config;

//Para convertir de una clase a otra

//Se usa para mapear automaticamente objetos de un tipo a otro.
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        //Se crea un nuevo objeto de tipo ModelMapper em memoria (heap)
        //Este sera el "traductor" que convierte Entities a DTO
        ModelMapper modelMapper = new ModelMapper();
        //Accedemos a la configuracion interna del objeto modelMapper recien creado
        //vamos a ajustar el comportamiento del mapeo
        modelMapper.getConfiguration()
                //sirve para mapear tambien atributos privados sin necesidad de getters y setters
                .setFieldMatchingEnabled(true)
                //aseguramos que el mapper pueda copiar datos aunque no existan setter publicos
                //A diferencia del de arriba este puede acceder aunque esten con private
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                //Aqui vamos a copiar la info con una estrategia STANDARD, la cual es felixble a la hora de copiar datos
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        //Aqui devolvemos el objeto como @Bean y podremos inyectarlo donde lo nececitemos
        return modelMapper;
    }
}
