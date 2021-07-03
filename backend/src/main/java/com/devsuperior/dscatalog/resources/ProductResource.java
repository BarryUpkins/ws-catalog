package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll( Pageable Pageable ) {
        Page<ProductDTO> list = service.findAllPaged( Pageable );
        return ResponseEntity.ok().body(list);
    }

    @GetMapping( value = "/{id}")   // tem que ser igual o param e ter @PathVariable
    public ResponseEntity<ProductDTO> findById( @PathVariable Long id ) {
        ProductDTO dto = service.findById( id );

        return ResponseEntity.ok().body( dto );
    }

    // 200 requisição sucesso
    // 201 inserção sucesso, convem informar no header o endereço
    // 204 success e corpo da resposta vazio
    @PostMapping    //( value = "/add")
    public ResponseEntity<ProductDTO> insert( @Valid @RequestBody ProductDTO dto ) { // @RequestBody o endpoint reconheça objeto e case com dto
        dto  = service.insert( dto );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( dto.getId() ).toUri();
        return ResponseEntity.created( uri ).body( dto );
    }

    @PutMapping( value = "/{id}" )
    public ResponseEntity<ProductDTO> update( @PathVariable Long id, @Valid @RequestBody ProductDTO dto ) {
        dto  = service.update( id, dto );
        return ResponseEntity.ok().body( dto );
    }

    @DeleteMapping( value = "/{id}" )
    public ResponseEntity<ProductDTO> delete( @PathVariable Long id ) {
        service.delete( id );
        return ResponseEntity.noContent().build();
    }
}