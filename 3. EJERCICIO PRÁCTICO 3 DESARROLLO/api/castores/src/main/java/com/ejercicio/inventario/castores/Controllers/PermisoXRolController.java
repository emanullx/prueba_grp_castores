package com.ejercicio.inventario.castores.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.inventario.castores.Entities.PermisoXRol;
import com.ejercicio.inventario.castores.Repositories.PermisoXRolRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/permisoxrol")
public class PermisoXRolController {
    @Autowired
    private PermisoXRolRepository permisoXRolRepository;

    @GetMapping("/obtenerTodos")
    public List<PermisoXRol> obtenerTodos() {
        return permisoXRolRepository.obtenerListado(Optional.empty(), Optional.empty()).orElseThrow(()-> new RuntimeException("No se encontró información"));
    }

    @PostMapping("/crear")
    public PermisoXRol crear(@RequestBody PermisoXRol permisoXRol) {
      
        return permisoXRolRepository.crear(permisoXRol).orElseThrow(()-> new RuntimeException("No se encontró pudo crear el permiso "));
    }

    @GetMapping("/obtener/{id}")
    public PermisoXRol obtener(@PathVariable("id") Long Id) {
        return permisoXRolRepository.obtener(Id).orElseThrow(()-> new RuntimeException("No se encontró información"));

    }
    
}
