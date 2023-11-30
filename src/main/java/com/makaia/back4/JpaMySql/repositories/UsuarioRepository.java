package com.makaia.back4.JpaMySql.repositories;

import com.makaia.back4.JpaMySql.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query
    public Usuario findByNombre(String nombre);
    @Query
    public Usuario findByApellido(String nombre);
}
