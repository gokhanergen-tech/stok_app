package com.stok.stokapp.Usecases.Common.Controller;

import com.stok.stokapp.Usecases.Common.Entities.User.City;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Security.Entities.Authority;
import com.stok.stokapp.Usecases.Security.Entities.NotTableObjects.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    private <T> ResponseEntity<Map> sendRequest(HttpEntity<T> httpEntity,String url){
         return this.testRestTemplate
                 .postForEntity(url,httpEntity, Map.class);
    }

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private UserController userController;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @PostConstruct
    private void initTest(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        this.testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    public void testSmoke() throws Exception{
        Assertions.assertNotNull(userController);
    }


    @Test
    public void testCreateUserTest() throws Exception{
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept","application/json");
        httpHeaders.setAccessControlAllowCredentials(true);
        User user=new User(null,"Gökhan","ERGEN","denemeABC5151","deneme6DASDasd","denemeuser@gmail.com",new City(null,"Niğde"),null, Set.of(new Authority(null,"ROLE_USER")));
        User user1=new User(null,"Gökhan","ERGEN","dene","deneme6DASDasd","denemeuser@gmail.com",new City(null,"Niğde"),null, Set.of(new Authority(null,"ROLE_USER")));
        User user2=new User(10l,"Gökhan","ERGEN","denemeABC5152","deneme6DA2SDasd","denemeuser1@gmail.com",new City(null,"Niğde"),null, Set.of(new Authority(null,"ROLE_USER")));

        User user3=new User(10l,"Gökhan","ERGEN","denemeABC5151","deneme6DASDasd","denemeuser@gmail.com",new City(null,"Niğde1"),null, Set.of(new Authority(null,"ROLE_USER")));

        HttpEntity<User> httpEntity=new HttpEntity<>(user,httpHeaders);
        Assertions.assertEquals(port, 8090);
        String url="http://localhost:"+port+"/api/register";
        Assertions.assertEquals(HttpStatus.CREATED.value(),sendRequest(httpEntity,url).getStatusCodeValue());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),sendRequest(new HttpEntity<>(user1,httpHeaders),url).getStatusCodeValue());
        Assertions.assertEquals(HttpStatus.CREATED.value(),sendRequest(new HttpEntity<>(user2,httpHeaders),url).getStatusCodeValue());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),sendRequest(new HttpEntity<>(user3,httpHeaders),url).getStatusCodeValue());

    }

    @Test
    public void testLogin() throws Exception{
        String url="http://localhost:"+port+"/api/login";
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept","application/json");
        httpHeaders.setAccessControlAllowCredentials(true);

        HttpEntity<LoginRequest> httpEntity=new HttpEntity<>(new LoginRequest("denemeABC5152","deneme6DA2SDasd"),httpHeaders);
        HttpEntity<LoginRequest> httpEntityUnhorized=new HttpEntity<>(new LoginRequest("denemeABC5152","deneme6DA2SDasd33"),httpHeaders);

        ResponseEntity<Map> response=sendRequest(httpEntity,url);

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatusCodeValue());
        Assertions.assertEquals(HttpStatus.OK.value(),testRestTemplate.getForEntity("http://localhost:"+port+"/api/auth/user",Map.class).getStatusCodeValue());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(),sendRequest(httpEntityUnhorized,url).getStatusCodeValue());
    }


}
