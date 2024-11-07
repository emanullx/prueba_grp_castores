package com.ejercicio.inventario.castores.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Producto;
import com.ejercicio.inventario.castores.Repositories.ProductoRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*" )
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/obtenerFiltro")
    public Filtro<Producto> obtenerFiltro(@RequestBody Filtro<Producto> filtro) {
        return productoRepository.obtenerFiltroListado(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PostMapping("/obtener")
    public List<Producto> obtenerListado(@RequestBody Filtro<Producto> filtro) {
        return productoRepository.obtener(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PostMapping("/crear/{idUsuario}/{ubicacion}")
    public Producto crear(@PathVariable("idUsuario") Long idUsuario, @PathVariable("ubicacion") String ubicacion, @RequestBody Producto producto) {
        
        return productoRepository.crear(producto, idUsuario, ubicacion).orElseThrow(()-> new RuntimeException("No se pudo crear el producto "));
    }
    
}
