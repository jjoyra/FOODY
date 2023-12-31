package com.foody.bookmark.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.bookmark.dto.response.BookmarkListResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;

class BookmarkControllerTest extends ControllerTest {

    private final String BASEURL = "/api/v1/bookmark";

    @Test
    @DisplayName("나의 북마크 목록 조회된다")
    void memberShouldGetBookmarkList() throws Exception {

        List<BookmarkListResponse> mockList = new ArrayList<>();
        List<String> ingredients = List.of("김치", "돼지고기", "대파", "고추가루");
        BookmarkListResponse bookmarkListResponse = new BookmarkListResponse(123456, "너무 맛있는 김치찌개", ingredients);
        mockList.add(bookmarkListResponse);

        when(bookmarkFacade.findBookmarkByMember(any())).thenReturn(mockList);

        setAuthentication();

        mockMvc.perform(
            get(BASEURL + "/mypage")
                .with(authentication(new TestingAuthenticationToken(new LoginInfo("lkm454545@gmail.com"), null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("/bookmark/list",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[].id").description("레시피 ID"),
                        fieldWithPath("[].name").description("레시피 이름"),
                        fieldWithPath("[].ingredients").description("레시피 재료 목록")
                    )
                )
            );
    }

    @Test
    @DisplayName("북마크 상태 변경된다")
    void bookmarkStatusShouldChanged() throws Exception {

        doNothing().when(bookmarkFacade).changeStatus(any(), any());
        setAuthentication();

        mockMvc.perform(
            post(BASEURL + "/{recipeId}", 123456L)
                .with(authentication(new TestingAuthenticationToken(new LoginInfo("lkm454545@gmail.com"), null)))
        ).andExpect(status().isNoContent())
            .andDo(
                document("/bookmark/status",
                    pathParameters(
                        parameterWithName("recipeId").description("상태 변경할 레시피 아이디, '북마크' 된 상황이면 해제하고, 아니라면 '북마크 등록' 한다.")
                    ))
            );
    }

}