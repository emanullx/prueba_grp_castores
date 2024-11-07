package com.ejercicio.inventario.castores.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Historico;
import com.ejercicio.inventario.castores.Repositories.HistoricoRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/historicos")
@CrossOrigin(origins = "*" )
public class HistoricoController {
    @Autowired
    private HistoricoRepository historicoRepository;

    @PostMapping("/obtenerFiltro")
    public Filtro<Historico> obtenerFiltro(@RequestBody Filtro<Historico> filtro) {
        return historicoRepository.obtenerFiltroListado(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

    @PostMapping("/obtener")
    public List<Historico> obtenerListado(@RequestBody Filtro<Historico> filtro) {
        return historicoRepository.obtener(filtro).orElseThrow(()-> new RuntimeException("No se encontró información "));
    }

}