package com.wink.gongongu.domain.favorite.repository;

import com.wink.gongongu.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
}
