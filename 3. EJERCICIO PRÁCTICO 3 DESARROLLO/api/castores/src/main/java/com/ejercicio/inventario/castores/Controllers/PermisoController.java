package com.ejercicio.inventario.castores.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.inventario.castores.Entities.Permiso;
import com.ejercicio.inventario.castores.Repositories.PermisoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {
    @Autowired
    private PermisoRepository permisoRepository;

    @GetMapping("/obtenerTodos")
    public List<Permiso> obtenerTodos() {
        return permisoRepository.obtenerListado().orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PostMapping("/crear")
    public Permiso crear(@RequestBody Permiso permiso) {     
        return permisoRepository.crear(permiso).orElseThrow(()-> new RuntimeException("No se pudo crear el permiso "));
    }

    @GetMapping("/obtener/{id}")
    public Permiso obtener(@PathVariable("id") Long Id) {
        return permisoRepository.obtener(Optional.of(Id), Optional.empty()).orElseThrow(()-> new RuntimeException("No se encontró el permiso: " + Id));
    }
    
}
