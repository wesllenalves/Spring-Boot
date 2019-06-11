/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;
import java.util.List;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/app")
/**
 *
 * @author laboratorio
 */
public class ProdutoWS {
    private ProdutoRepository repository;
    
    public ProdutoWS(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }
    
    
    @RequestMapping(value = "/produto", method = RequestMethod.GET)
    public List<Produto> getTodosOsProdutos(){
        return repository.findAll();
    }
    
    @GetMapping("/produto/{id}")
    public Resource findById(@PathVariable long id){
        Link link = linkTo(DemoApplication.class).slash("app").withSelfRel();
        Resource<Produto> retorno = new Resource(repository.findById(id), link);
        return retorno;
    }
    
    @PostMapping("/produto")
    public Produto create(@RequestBody Produto produto){
        return repository.save(produto);
    }
    
    @PutMapping(value = "/produto/{id}")
    public ResponseEntity update(@PathVariable("id") long id, @RequestBody Produto produto){
        return repository.findById(id)
                .map(record -> { 
                    record.setNome(produto.getNome());
                    record.setQtdEstoque(produto.getQtdEstoque());
                    record.setValorUnitario(produto.getValorUnitario());
                    Produto updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                
                }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping(path ={"produto/{id}"})
    public  ResponseEntity<?> delete(@PathVariable long id){
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
    
    
    
    
    
    
    
    @GetMapping("pi")
    public String pi(){
        return "3.14";
    }
    
    @RequestMapping(value = "/pi2", method = RequestMethod.GET)
    public String pi2() {
        return "3.14";
    }
    
    
}
