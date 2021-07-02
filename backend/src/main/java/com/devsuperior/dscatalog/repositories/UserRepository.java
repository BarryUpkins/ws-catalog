package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
//            + "(COALESCE(:categories) IS NULL OR cats IN :categories) AND "
//            + "(LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))) ")
//    Page<Product> find( List<Category> categories, String name, Pageable pageable );
}