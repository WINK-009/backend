package com.wink.gongongu.domain.post.service;

import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
import com.wink.gongongu.domain.favorite.service.FavoriteService;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.participant.service.ParticipantService;
import com.wink.gongongu.domain.post.dto.PostDetailResponse;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostImage;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.post.repository.PostImageRepository;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wink.gongongu.global.service.S3ImageService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ParicipantRepository participantRepository;
    private final ParticipantService participantService;
    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;
    private final S3ImageService s3ImageService;
    private final PostImageRepository postImageRepository;

    @Transactional
    public Long postRegister(Long userId, List<MultipartFile> images, UploadPostRequest request) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음: " + userId));

        Post post;
        if (user.getUserType() == UserType.BUSINESS) {
            validateBusiness(request);
            post = Post.create(user, request, PostType.BUSINESS);
        } else {
            validateIndividual(request);
            post = Post.create(user, request, PostType.INDIVIDUAL);
        }
        Post saved = postRepository.save(post);
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile f = images.get(i);
                if (f == null || f.isEmpty()) continue;

                String url = s3ImageService.uploadImage(f);

                // 첫 번째 업로드(i==0)만 is_main=true
                boolean isMain = (i == 0);

                postImageRepository.save(PostImage.of(saved, url, isMain));
            }
        }


        participantRepository.save(Participant.host(user, saved));

        return saved.getPostId();


    }
    private void validateBusiness(UploadPostRequest req) {
        // BUSINESS: price 필수
        if (req.originalprice() == null)
            throw new IllegalArgumentException("BUSINESS는 original price(price) 필수");

        // BUSINESS: region 필요 없음 -> 아예 받지 않도록 null 강제(권장)
        if (req.region() != null && !req.region().isBlank())
            throw new IllegalArgumentException("BUSINESS는 region을 입력하지 않습니다");
    }

    private void validateIndividual(UploadPostRequest req) {
        // INDIVIDUAL: price 없음 -> 들어오면 에러(권장)
        if (req.originalprice() != null)
            throw new IllegalArgumentException("INDIVIDUAL은 original price(price)를 입력하지 않습니다");

        // INDIVIDUAL: region 필수
        if (req.region() == null || req.region().isBlank())
            throw new IllegalArgumentException("INDIVIDUAL은 region 필수");
    }

    @Transactional
    public List<PostListResponse> getPostList(int page, int size){
        /* 원래 이걸로 됏는데, 그 joinedQuantity 추가하면서 바꿨다
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.findAll(pageable)
                .map(PostListResponse::from)
                .getContent();*/
        // 1) postId 목록
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, size)
        );
        List<Long> postIds = posts.getContent().stream()
                .map(Post::getPostId)
                .toList();

        // 2) postId -> joinedSum 맵 만들기
        Map<Long, Integer> sumMap = participantRepository.sumJoinedQuantityByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getJoinedQuantitySum() == null ? 0 : r.getJoinedQuantitySum()
                ));
        Map<Long, Integer> favMap = favoriteRepository.countFavByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getFavCount() == null ? 0 : r.getFavCount().intValue()
                ));
        Map<Long, String> mainImageMap = postImageRepository.findMainImagesByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostImageRepository.MainImageRow::getPostId,
                        PostImageRepository.MainImageRow::getImageUrl
                ));

        // 3) DTO로 변환 (없으면 0)
        return posts.getContent().stream()
                .map(p -> PostListResponse.from(p, sumMap.getOrDefault(p.getPostId(), 0), favMap.getOrDefault(p.getPostId(),0), mainImageMap.getOrDefault(p.getPostId(), null)))
                .toList();
    }

    @Transactional
    public PostDetailResponse getPostDetail(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음: " + postId));

        String mainImageUrl = postImageRepository.findByPostId_PostIdAndIsMainTrue(postId)
                .map(PostImage::getImageUrl)
                .orElse(null);

        List<String> imageUrls = postImageRepository.findAllByPostId_PostIdOrderByImageIdAsc(postId)
                .stream()
                .map(PostImage::getImageUrl)
                .toList();

        int joinedSum = participantRepository.sumJoinedQuantity(postId);
        return PostDetailResponse.from(post, joinedSum, mainImageUrl, imageUrls);

    }

    public Page<PostListResponse> searchPosts(
            String query,
            String region,
            PostType type,
            PostStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        /*
        return postRepository.search(query, region, type, status, pageable)
                .map(PostListResponse::from);
         */
        Page<Post> postPage = postRepository.search(query, region, type, status, pageable);
        List<Post> posts = postPage.getContent();

        if (posts.isEmpty()) {
            return postPage.map(PostListResponse::from); // 빈 경우는 그냥 기존대로
        }

        // postIds 뽑기
        List<Long> postIds = posts.stream().map(Post::getPostId).toList();

        // joinedSum map 만들기
        Map<Long, Integer> joinedSumMap = participantRepository
                .sumJoinedQuantityByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        ParicipantRepository.PostJoinedSumRow::getPostId,
                        r -> r.getJoinedQuantitySum() == null ? 0 : r.getJoinedQuantitySum()
                ));

        Map<Long, Integer> joinedFavMap = favoriteRepository.countFavByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getFavCount() == null ? 0 : r.getFavCount().intValue()
                ));

        Map<Long, String> mainImageMap = postImageRepository.findMainImagesByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostImageRepository.MainImageRow::getPostId,
                        PostImageRepository.MainImageRow::getImageUrl
                ));

        // Page<PostListResponse> 만들기 (pageable/total 유지)
        List<PostListResponse> dtoList = posts.stream()
                .map(p -> PostListResponse.from(p, joinedSumMap.getOrDefault(p.getPostId(), 0), joinedFavMap.getOrDefault(p.getPostId(),0), mainImageMap.getOrDefault(p.getPostId(), null)))
                .toList();

        return new PageImpl<>(dtoList, pageable, postPage.getTotalElements());

    }
    @Transactional
    public void deletePost(Long userId, Long postId){
        Post post = postRepository.findByPostId(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자 없음"));

        if (post.getUserId() == user){

            participantRepository.deleteByPostId_PostId(postId);
            favoriteRepository.deleteByPostId_PostId(postId);
            postRepository.delete(post);
        }
    }

    @Transactional
    public List<PostListResponse> myPost(Long userId){
        // 1) 내가 만든 글 목록
        List<Post> posts = postRepository.findAllByUserId_IdOrderByCreatedAtDesc(userId);

        if (posts.isEmpty()) return List.of();

        // 2) postIds 추출
        List<Long> postIds = posts.stream()
                .map(Post::getPostId)
                .toList();

        // 3) 참여 수량 합계 Map (필요 없으면 0으로 넣어도 되지만, 있는 게 더 좋음)
        Map<Long, Integer> sumMap = participantRepository.sumJoinedQuantityByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getJoinedQuantitySum() == null ? 0 : r.getJoinedQuantitySum()
                ));

        // 4) 찜 수 Map
        Map<Long, Integer> favMap = favoriteRepository.countFavByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getPostId(),
                        r -> r.getFavCount() == null ? 0 : r.getFavCount().intValue()
                ));

        Map<Long, String> mainImageMap = postImageRepository.findMainImagesByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        PostImageRepository.MainImageRow::getPostId,
                        PostImageRepository.MainImageRow::getImageUrl
                ));



        return posts.stream()
                .map(p -> PostListResponse.from(
                        p,
                        sumMap.getOrDefault(p.getPostId(), 0),
                        favMap.getOrDefault(p.getPostId(), 0),
                        mainImageMap.getOrDefault(p.getPostId(), null)
                ))
                .toList();

    }


}
