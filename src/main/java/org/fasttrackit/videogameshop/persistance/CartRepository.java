package org.fasttrackit.videogameshop.persistance;

import org.fasttrackit.videogameshop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
