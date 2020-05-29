package com.galid.jpastudy;

import com.galid.jpastudy.common.BaseIntegrationTest;
import com.galid.jpastudy.config.UserSetUp;
import com.galid.jpastudy.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserSetUp userSetUp;

    @Test
    public void 유저생성() throws Exception {
        //given
        String USER_NAME = "TEST";
        CreateUserRequest createRequest = new CreateUserRequest(USER_NAME);

        //when
        ResultActions resultActions = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId", is(notNullValue())));
    }

    @Test
    public void 유저조회() throws Exception {
        //given
        String USER_NAME = "TEST";
        long USER_ID = userSetUp.saveUser(USER_NAME);

        //when
        ResultActions resultActions = mvc.perform(get("/users/{userId}", USER_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value(USER_NAME));
    }

    @Test
    public void 유저전체조회() throws Exception {
        //given
        String USER_1_NAME = "TEST1";
        String USER_2_NAME = "TEST2";
        userSetUp.saveUser(USER_1_NAME);
        userSetUp.saveUser(USER_2_NAME);

        //when
        ResultActions resultActions = mvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userList", hasSize(2)))
                .andExpect(jsonPath("$.userList[0].userName").value(USER_1_NAME))
                .andExpect(jsonPath("$.userList[1].userName").value(USER_2_NAME));
    }

}