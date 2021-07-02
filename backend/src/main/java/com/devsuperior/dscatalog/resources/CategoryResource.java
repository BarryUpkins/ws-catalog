package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping( value = "/categories" )
public class CategoryResource {

    @Autowired
    private CategoryService service;

//    @GetMapping
//    public ResponseEntity<List<CategoryDTO>> findAll(){
////        List<Category> list = new ArrayList<>();
////            list.add( new Category( 1L, "Books" ) );
////            list.add( new Category( 1L, "Electronics" ) );
//
//        List<CategoryDTO> list = service.findAll();
//        return ResponseEntity.ok().body( list );
//    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {

        PageRequest pageRequest = PageRequest.of( page, linesPerPage, Sort.Direction.valueOf( direction ), orderBy );
//        Page<CategoryDTO> list = service.findAllPaged( pageRequest );
//        list.add(new Category(1L, "Books"));
//        list.add(new Category(1L, "Electronics"));
        Page<CategoryDTO> list = service.findAllPaged( pageRequest );
        return ResponseEntity.ok().body( list );
//        return ResponseEntity.ok().body(list);
    }

    @GetMapping( value = "/{id}" )
    public ResponseEntity<CategoryDTO> findById( @PathVariable Long id ){
        CategoryDTO dto = service.findById( id );
        return ResponseEntity.ok().body( dto );
    }

    @PostMapping( value = "/add" )
    public ResponseEntity<CategoryDTO> insert( @RequestBody CategoryDTO dto ){
        dto = service.insert( dto );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( dto.getId() ).toUri();
        return ResponseEntity.created( uri ).body( dto );
    }

    @PutMapping( value = "/update/{id}" )
    public ResponseEntity<CategoryDTO> insert( @PathVariable Long id, @RequestBody CategoryDTO dto ){
        dto = service.update( id, dto );
        return ResponseEntity.ok().body( dto );
    }

    @DeleteMapping( value = "/delete/{id}" )
    public ResponseEntity<CategoryDTO> delete( @PathVariable Long id ){
        service.delete( id );
        return ResponseEntity.noContent().build();
    }

}
