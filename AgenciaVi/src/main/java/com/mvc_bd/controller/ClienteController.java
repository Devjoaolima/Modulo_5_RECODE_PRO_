package com.mvc_bd.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mvc_bd.model.Cliente;
import com.mvc_bd.repository.ClienteRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/Destino")
    public ModelAndView Destino() {
        ModelAndView page = new ModelAndView("cliente/Destino");
        page.addObject("cliente", new Cliente());
        return page;
    }
    
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView page = new ModelAndView("cliente/index");
        page.addObject("cliente", new Cliente());
        return page;
    }

    @GetMapping
    public ModelAndView listar() {
        ModelAndView page = new ModelAndView("cliente/listar");
        List<Cliente> clientes = clienteRepository.findAll();
        page.addObject("clientes", clientes);
        return page;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView page = new ModelAndView("cliente/cadastro");
        page.addObject("cliente", new Cliente());
        return page;
    }
    //cliente/Destino
    @PostMapping("/cadastrar")
    public ModelAndView cadastrar(Cliente cliente, @RequestParam("fileCliente") MultipartFile file) throws IOException {

        try {
            cliente.setImagem(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/cliente");

        clienteRepository.save(cliente);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/detalhar");
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @GetMapping("/imagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagen(@PathVariable("id") Long id) {
        Cliente cliente = this.clienteRepository.findById(id).orElse(null);
        return ResponseEntity.ok().body(cliente != null ? cliente.getImagem() : new byte[0]);
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/edicao");
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @PostMapping("/{id}/editar")
    public ModelAndView editar(Cliente cliente, @RequestParam("fileCliente") MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                cliente.setImagem(file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        clienteRepository.save(cliente);
        ModelAndView modelAndView = new ModelAndView("redirect:/cliente");

        return modelAndView;
    }

    @GetMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cliente");

        clienteRepository.deleteById(id);

        return modelAndView;
    }
    
    @GetMapping("/Promocoes")
    public ModelAndView Promocoes() {
        ModelAndView page = new ModelAndView("cliente/Promocoes");
        page.addObject("cliente", new Cliente());
        return page;
    }
    
    
}
