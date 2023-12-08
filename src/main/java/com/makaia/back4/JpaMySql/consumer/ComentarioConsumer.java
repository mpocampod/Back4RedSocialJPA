package com.makaia.back4.JpaMySql.consumer;

import com.makaia.back4.JpaMySql.dtos.ComentarioDTO;
import com.makaia.back4.JpaMySql.entities.Comentario;
import com.makaia.back4.JpaMySql.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class ComentarioConsumer {

    private final UsuarioService usuarioService;

    @Autowired
    public ComentarioConsumer(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RabbitListener(queues = { "comment_created" })
    public void recibirComentario(@Payload ComentarioDTO comentarioDTO) {

        Long idUsuario = comentarioDTO.getUsuarioId();

        Comentario comentario = new Comentario(comentarioDTO.getContenido());

        this.usuarioService.asociarComentario(idUsuario, comentario);
    }

}
