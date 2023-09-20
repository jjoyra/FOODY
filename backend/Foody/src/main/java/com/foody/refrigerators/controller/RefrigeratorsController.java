package com.foody.refrigerators.controller;

import com.foody.global.entity.UserInfo;
import com.foody.refrigerators.dto.request.InsertCustomIngredientRequest;
import com.foody.refrigerators.dto.request.InsertIngredientRequest;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/refrigerators")
@RequiredArgsConstructor
public class RefrigeratorsController {

    private final RefrigeratorsService refrigeratorsService;

    @GetMapping("/ingredient")
    public ResponseEntity<List<SearchIngredientResponse>> searchIngredient(String keyword) {
        log.debug("keyword : " + keyword);
        List<SearchIngredientResponse> ingredients = refrigeratorsService.searchIngredient(keyword);
        return ResponseEntity.ok().body(ingredients);
    }

    @PostMapping("/")
    public ResponseEntity<String> registerIngredient(
            @RequestBody List<Integer> ingredients,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        refrigeratorsService.insertIngredient(userInfo.getEmail(), ingredients);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ingredient")
    public ResponseEntity<String> registerCustomIngredient(
            @RequestBody List<InsertCustomIngredientRequest> ingredients,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        refrigeratorsService.insertCustomIngredient(userInfo.getEmail(), ingredients);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/")
//    public ResponseEntity<List<>> getUserRefrigerator(@AuthenticationPrincipal UserInfo userInfo) {
//
//
//    }

    @DeleteMapping("/")
    public ResponseEntity<String> resetRefrigerator(@AuthenticationPrincipal UserInfo userInfo) {
        refrigeratorsService.resetRefrigerator(userInfo.getEmail());
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/")
//    public ResponseEntity<String> deleteIngredient(
//            @AuthenticationPrincipal UserInfo userInfo
//            ) {
//        return ResponseEntity.ok().build();
//    }
}
