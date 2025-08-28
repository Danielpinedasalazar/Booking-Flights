package com.airline.danielairlines.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Cuando se tiene frontend y backend en dominios distintos, por seguridad el navegador bloquea las peticiones.
//Por ejemplo: si React intenta llamar a la API el navegador lo bloquea a menos de que tenga habilitado CORS.

@Configuration
//CorsConfig: Nombre de la clase
public class CorsConfig {

    @Bean
    //WebMvcConfigurer: Interfaz de Spring que permite personalizar el comportamiento
    //corsConfigurer: nombre del bean/metodo.
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            //addCorsMappings: metodo "hook" que Spring llama para registrar reglas de CORS
            //CorsRegistry: nos permite registrar configuraciones de CORS.
            public void addCorsMappings(CorsRegistry registry) {
                //Habilitamos paa que todas las rutas permitan los cors
                registry.addMapping("/**")
                        //Definimos los metodos HTTP qhe se van a permitir desde otros origenes
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        //Cuando se tenga el front listo pondremos aqui la url para que solo desde alli se permitan las peticiones
                        .allowedOrigins("*");
            }
        };
    }
}
