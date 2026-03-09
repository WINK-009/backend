package com.wink.gongongu.domain.favorite.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.favorite.service.FavoriteService;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/posts/{postId}/fav")
    public ResponseEntity<Void> addFav(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long postId
    ){
        favoriteService.addFavorite(principal.userId(), postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}/fav")
    public ResponseEntity<Void> deleteFav(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long postId
    ){
        favoriteService.deleteFavorite(principal.userId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/fav")
    public ResponseEntity<List<PostListResponse>> myFav(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(favoriteService.findFavPosts(principal.userId()));
    }
}
