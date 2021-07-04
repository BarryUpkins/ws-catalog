package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll( Pageable pageable ) {
        Page<UserDTO> list = service.findAllPaged( pageable );
        return ResponseEntity.ok().body(list);
    }

    @GetMapping( value = "/{id}")   // tem que ser igual o param e ter @PathVariable
    public ResponseEntity<UserDTO> findById( @PathVariable Long id ) {
        UserDTO dto = service.findById( id );

        return ResponseEntity.ok().body( dto );
    }

    // 200 requisição sucesso
    // 201 inserção sucesso, convem informar no header o endereço
    // 204 success e corpo da resposta vazio
    @PostMapping( value = "/add")
    public ResponseEntity<UserDTO> insert( @Valid @RequestBody UserInsertDTO dto ) { // @RequestBody o endpoint reconheça objeto e case com dto
        UserDTO newDto  = service.insert( dto );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( newDto.getId() ).toUri();
        return ResponseEntity.created( uri ).body( newDto );
    }

    @PutMapping( value = "/{id}" )
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto ) {
        UserDTO newDto = service.update( id, dto );
//        UserDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body( newDto );
    }

    @DeleteMapping( value = "/{id}" )
    public ResponseEntity<UserDTO> delete( @PathVariable Long id ) {
        service.delete( id );
        return ResponseEntity.noContent().build();
    }
}