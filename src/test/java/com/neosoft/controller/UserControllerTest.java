package com.neosoft.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.collection.User;
import com.neosoft.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import static org.assertj.core.api.Assertions.assertThat;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(value = UserController.class)*/

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class UserControllerTest {

   /* @MockBean
    private IUserService  userService;

    @Autowired
    private MockMvc  mockMvc;*/


    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;
    @Test
    public void testSaveUser() throws Exception {




        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

      //  when(userRepository.save(any(User.class))).thenReturn(true);

        User user = new User();


        user.setUser_id("SBIN234");
        user.setUsername("Raja");
        user.setAge(34);
        user.setSurname("Babu");

        ResponseEntity<?> responseEntity = userController.saveUser(user);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");



        /*User mockUser = new User();

        mockUser.setUser_id("SBIN234");
        mockUser.setUsername("Raja");
        mockUser.setAge(34);
        mockUser.setSurname("Babu");
        //mockUser.setDob(new Date("1993-10-13T12:10:32.809+0000"));
        //mockUser.setJoiningDate(new Date("2021-10-13T12:10:32.809+0000"));

        String inputInJson = this.mapToJson(mockUser);

        String URI ="/user/save";

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(String.valueOf(mockUser));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String outputInJson =  response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
*/
    }

    /**
     * Maps an object into a JSON String. Uses a Jackson ObjectMapper
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    private String mapToJson(Object  object) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(object);
    }
}
