package ru.javaops.topjava.web.vote;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.VoteRepository;
import ru.javaops.topjava.util.JsonUtil;
import ru.javaops.topjava.web.AbstractControllerTest;
import ru.javaops.topjava.web.user.UserTestData;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava.web.vote.VoteController.REST_URL;
import static ru.javaops.topjava.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votes));
    }
    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_VOTE_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteOldVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE1_ID))
                .andExpect(status().isNotModified());
        assertTrue(voteRepository.get(UserTestData.USER_ID, VOTE1_ID).isPresent());
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateOld() throws Exception {
        //TODO - consider time constraints, now test is failed
        Vote updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNotModified());
    }

}