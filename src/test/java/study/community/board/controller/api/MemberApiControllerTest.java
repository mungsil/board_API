package study.community.board.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Rollback(value = false)
@Transactional
@AutoConfigureMockMvc
class MemberApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 컨트롤러테스트() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/test") //url
                //.param("name","myName") //parameter
                )
                .andExpect(status().isOk()) //response status 200 검증
                .andExpect(jsonPath("method").value("GET")); //response method 데이터 검증
                //.andExpect(jsonPath("name").value("myName") //response name 데이터 검증
    }

    @Test
    public void findMemberById() throws Exception{
        //given

        //when

        //then

    }

}