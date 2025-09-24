package com.cisco.asycexamples.aggregator.client;

import com.cisco.asycexamples.aggregator.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserRestClient implements CommandLineRunner {
    @Autowired
    RestTemplate template;

    @Override
    public void run(String... args) throws Exception {
//        getUser();
//        getUsers();
         getUsersString();
         addUser();
    }

    private void addUser() {
        UserDTO userDTO = new UserDTO(24, "John", "john@cisco.com");
        template.postForEntity("https://jsonplaceholder.typicode.com/users", userDTO, UserDTO.class);
    }

    private void getUsersString() {
       String response = template.getForObject("https://jsonplaceholder.typicode.com/users", String.class);
        System.out.println(response); // JSON
    }

    private void getUsers() {
        ResponseEntity<List<UserDTO>> usersResponse =
                template.exchange("https://jsonplaceholder.typicode.com/users", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<UserDTO>>() {
                        });

        System.out.println(usersResponse.getBody());
    }

    private void getUser() {
       ResponseEntity<UserDTO> userResponse =
               template.getForEntity("https://jsonplaceholder.typicode.com/users/1", UserDTO.class);
        System.out.println(userResponse.getStatusCode());
        System.out.println(userResponse.getBody());
    }
}
