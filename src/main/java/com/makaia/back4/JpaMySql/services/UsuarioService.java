package com.makaia.back4.JpaMySql.services;

import com.makaia.back4.JpaMySql.dtos.CrearUsuarioDTO;
import com.makaia.back4.JpaMySql.entities.Usuario;
import com.makaia.back4.JpaMySql.exceptions.RedSocialApiException;
import com.makaia.back4.JpaMySql.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crear(CrearUsuarioDTO dto) {
        Usuario exists = this.repository.findByNombre(dto.getNombre());
        if(exists != null){
            throw new RedSocialApiException("User aleady exists", HttpStatusCode.valueOf(400));
        }
        Usuario nuevoUsuario = new Usuario(dto.getNombre(), dto.getApellido(), dto.getDireccion(), dto.getEdad());
        return this.repository.save(nuevoUsuario);
    }

    private boolean isEmpty(String valor){
        return valor == null || valor.isEmpty();
    }


    public List<Usuario> listar() {
        return this.repository.findAll();
    }
}
