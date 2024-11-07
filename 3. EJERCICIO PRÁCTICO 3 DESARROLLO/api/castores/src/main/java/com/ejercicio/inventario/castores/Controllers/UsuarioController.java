package com.ejercicio.inventario.castores.Controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ejercicio.inventario.castores.Entities.PermisoXRol;
import com.ejercicio.inventario.castores.Entities.Rol;
import com.ejercicio.inventario.castores.Entities.Usuario;
import com.ejercicio.inventario.castores.Repositories.PermisoXRolRepository;
import com.ejercicio.inventario.castores.Repositories.RolRepository;
import com.ejercicio.inventario.castores.Repositories.UsuarioRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*" )
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PermisoXRolRepository permisoXRolRepository;

    @PostMapping("/crear")
    public Usuario crear(@RequestBody Usuario usuario) {     
        return usuarioRepository.crear(usuario).orElseThrow(()-> new RuntimeException(" Error al crear usuario. "));
    }

    @GetMapping("/obtener/{id}")
    public Usuario obtener(@PathVariable("id") Long Id) {
        return usuarioRepository.obtener(Optional.of(Id), Optional.empty(), Optional.empty(), Optional.empty()).orElseThrow(()-> new RuntimeException("No se encontró el usuario: " + Id));
    }
    
    @PostMapping("/validar")
    public Usuario validar(@RequestBody Usuario usuario) {
        Usuario usuarioActual = usuarioRepository.obtener(Optional.empty(), Optional.of(usuario.getNombre()), Optional.empty(), Optional.of(1)).orElseThrow(()-> new RuntimeException("No se encontró el usuario: " + usuario.getNombre()));
        
        if(!usuario.getContraseña().equals(usuarioActual.getContraseña()))
            throw new RuntimeException("Credenciales incorrectas");

        usuario.setContraseña("");
        Rol rol = rolRepository.obtener(Optional.of(usuarioActual.getRol().getId()), Optional.empty()).orElseThrow(()-> new RuntimeException(" No tiene rol. "));
        List<PermisoXRol> permisos = permisoXRolRepository.obtenerListado(Optional.of(rol.getId()), Optional.empty()).orElseThrow(()-> new RuntimeException(" No tiene permisos. "));    
        rol.setPermisos(permisos);
        usuario.setRol(rol);
        usuario.setId(usuarioActual.getId());

        return usuario;
    }
}
