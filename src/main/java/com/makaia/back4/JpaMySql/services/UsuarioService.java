package com.makaia.back4.JpaMySql.services;

import com.makaia.back4.JpaMySql.dtos.CrearPublicacionDTO;
import com.makaia.back4.JpaMySql.dtos.CrearUsuarioDTO;
import com.makaia.back4.JpaMySql.entities.Comentario;
import com.makaia.back4.JpaMySql.entities.Publicacion;
import com.makaia.back4.JpaMySql.entities.Usuario;
import com.makaia.back4.JpaMySql.exceptions.RedSocialApiException;
import com.makaia.back4.JpaMySql.publisher.Publisher;
import com.makaia.back4.JpaMySql.repositories.ComentarioRepository;
import com.makaia.back4.JpaMySql.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UsuarioService {
    UsuarioRepository repository;
    private final ComentarioRepository comentarioRepository;

    Publisher publisher;

    @Autowired
    public UsuarioService(UsuarioRepository repository, Publisher publisher,
            ComentarioRepository comentarioRepository) {
        this.repository = repository;
        this.publisher = publisher;
        this.comentarioRepository = comentarioRepository;
    }

    public Usuario crear(CrearUsuarioDTO dto) {
        Usuario exists = this.repository.findByNombre(dto.getNombre());
        if (exists != null) {
            throw new RedSocialApiException("User aleady exists", HttpStatusCode.valueOf(400));
        }
        Usuario nuevoUsuario = new Usuario(dto.getNombre(), dto.getApellido(), dto.getDireccion(), dto.getEdad());
        nuevoUsuario = this.repository.save(nuevoUsuario);

        System.out.println(nuevoUsuario);

        crearPublicacionPorDefecto(nuevoUsuario.getId());

        return nuevoUsuario;
    }

    private void crearPublicacionPorDefecto(Long userId) {
        this.publisher.send(userId);
    }

    private Publicacion crearPublicacionPorDefectoOld(Long userId) {
        RestTemplate template = new RestTemplate();
        CrearPublicacionDTO publicacionDto = new CrearPublicacionDTO("Mi primera publicacion",
                "El contenido",
                userId);
        ResponseEntity<Publicacion> responseEntity = template.postForEntity(
                "http://localhost:8080/api/v1/publicaciones",
                publicacionDto,
                Publicacion.class);
        return responseEntity.getBody();
    }

    private boolean isEmpty(String valor) {
        return valor == null || valor.isEmpty();
    }

    public List<Usuario> listar() {
        return StreamSupport
                .stream(this.repository.findAll().spliterator(), false)
                .toList();
    }

    public Usuario asociarComentario(Long idUsuario, Comentario comentario) {

        Usuario usuario = this.repository
                .findById(idUsuario)
                .orElseThrow(() -> new RedSocialApiException("El usuario no existe"));

        comentario.setUsuario(usuario);

        Comentario comentarioGuardado = this.comentarioRepository.save(comentario);

        usuario.getComentarios().add(comentarioGuardado);

        return this.repository.save(usuario);
    }
}
