package com.airline.danielairlines.security;

import com.airline.danielairlines.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
//Implements es como decir: yo prometo implementar todos los metodos que pide la interfaz UserDetails
public class AuthUser implements UserDetails {

    //Guarda la entidad real de usuario dentro de auth user
    //No pasamos el dto debido a que necesitamos la contraseña
    private User user;

    @Override
    //Collection: es una coleccion generica de Java (lista, set, etc.)
    //? extends GrantedAuthority: cualquier objeto que implemente la interfaz GrantedAuthority
    //getAuthorities: para saber que permisos o roles tiene el usuario
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //devolvemos el usuario con los roles
        return user.getRoles()
                //.stream() permite procesar los datos con operaciones encadenadas (map, filter, etc.)
                .stream()
                //.map: toma cada elemento de la lista y lo transforma en otra cosa
                //role: parametro (cada elemento del stream))
                // -> : es decir entro estoy sale esto
                //creamos un nuevo objeto de tipo SimpleGrantedAuthority
                //SimpleGrantedAuthority: representa un permiso o rol que tiene un usuario
                .map( role -> new SimpleGrantedAuthority(role.getName()))
                //.toList() convierte el stream en una lista inmutable
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
