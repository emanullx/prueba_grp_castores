package com.ejercicio.inventario.castores.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Inventario;
import com.ejercicio.inventario.castores.Repositories.InventarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*" )
public class InventarioController {
    @Autowired
    private InventarioRepository inventarioRepository;

    @PostMapping("/obtenerFiltro")
    public Filtro<Inventario> obtenerFiltro(@RequestBody Filtro<Inventario> filtro) {
        return inventarioRepository.obtenerFiltroListado(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PostMapping("/obtener")
    public List<Inventario> obtenerListado(@RequestBody Filtro<Inventario> filtro) {
        return inventarioRepository.obtener(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PutMapping("/actualizar/{idUsuario}/{idProducto}")
    public Inventario actualizar(@RequestBody Inventario inventario, @PathVariable("idUsuario") Long idUsuario,@PathVariable("idProducto") Long idProducto) {
        return inventarioRepository.actualizar(inventario, idUsuario, idProducto).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }
}