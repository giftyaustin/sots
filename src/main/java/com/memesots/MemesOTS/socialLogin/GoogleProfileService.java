package com.memesots.MemesOTS.socialLogin;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.memesots.MemesOTS.ExceptionHandlers.AppException;

@Component
public class GoogleProfileService {
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleProfile getProfile(String accessToken) throws AppException {
        String url = "https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses,photos";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GoogleProfile> response = restTemplate.exchange(url, HttpMethod.GET, entity, GoogleProfile.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Handle error
            if(e.getStatusCode().value() == 404){
                throw new AppException(false, 404, "Invalid ggogle api");
            }
            throw new AppException(false, 401, "Invalid access token");
        }
    }
}
