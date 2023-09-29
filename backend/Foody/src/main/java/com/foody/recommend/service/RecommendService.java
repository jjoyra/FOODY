package com.foody.recommend.service;

import com.foody.global.exception.ErrorCode;
import com.foody.recipe.dto.response.RecipeListResponse;
import com.foody.recipe.service.RecipeService;
import com.foody.recommend.dto.resquest.IngredientInput;
import com.foody.recommend.exception.RecommendException;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RefrigeratorsService refrigeratorsService;
    private final RecipeService recipeService;
    @Value("${recommend.server.url}")
    private String serverUrl;

    @Transactional(readOnly = true)
    public List<RecipeListResponse> findRecommendItemByIngredients(String email) {

        List<UserRefrigeratorResponse> refrigerator = refrigeratorsService.getUserRefrigerator(email);

        if (refrigerator.isEmpty()) {
            throw new RecommendException(ErrorCode.REFRIGERATOR_IS_EMPTY);
        }

        String ingredients = getIngredientsString(refrigerator);
        List<Long> recipeIds = ingredientSendToServer(ingredients);

        return recipeService.findRecipeListByRecommend(recipeIds);
    }

    private String getIngredientsString(List<UserRefrigeratorResponse> refrigerator) {

        return refrigerator.stream()
                           .map(UserRefrigeratorResponse::ingredientName)
                           .collect(Collectors.joining(" "));
    }

    // TODO : 테스트를 위해 PUBLIC으로 열어둠, 나중에 Private으로 닫아야 함
    public List<Long> ingredientSendToServer(String ingredients) {

        IngredientInput ingredientInput = new IngredientInput(ingredients, 5);

        WebClient webClient = WebClient.builder()
                                       .build();

        URI uri = URI.create(serverUrl + "/recipes/ingredients");
        log.debug("starting inter-server communication for ingredient");
        List<Long> recommendItemList = webClient.post()
                                                         .uri(uri)
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .body(BodyInserters.fromValue(ingredientInput))
                                                         .retrieve()
                                                         .onStatus(HttpStatus::is5xxServerError,
                                                             response -> Mono.error(
                                                                 new RecommendException(
                                                                     ErrorCode.BIGDATA_SERVER_ERROR)
                                                             ))
                                                         .bodyToMono(
                                                             new ParameterizedTypeReference<List<Long>>() {
                                                             })
                                                         .block();

        log.debug("inter-server communication terminated");

        return recommendItemList;
    }
}