package com.ejercicio.inventario.castores.Controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ejercicio.inventario.castores.Entities.Rol;
import com.ejercicio.inventario.castores.Repositories.RolRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/obtenerTodos")
    public List<Rol> obtenerTodos() {
        return rolRepository.obtenerListado().orElse(List.of());
    }

    @PostMapping("/crear")
    public Rol crear(@RequestBody Rol rol) {
        return rolRepository.crear(rol).orElseThrow(()->new RuntimeException("No se pudo crear el rol."));
    }

    @GetMapping("/obtener/{id}")
    public Rol obtener(@PathVariable("id") Long Id) {
        return rolRepository.obtener(Optional.of(Id), Optional.empty()).orElseThrow(()-> new RuntimeException("No se encontró el rol: " + Id));
    }
    
}
