package example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardHttpPostTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateANewCashCard() {
        CashCard newCashCard = new CashCard(null, 250.00, "sarah");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = createResponse.getHeaders().getLocation();
        ResponseEntity<String> resp = restTemplate.getForEntity(location, String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Add assertions such as these
// DocumentContext documentContext = JsonPath.parse(createResponse.getBody());
// Number id = documentContext.read("$.id");
// Double amount = documentContext.read("$.amount");

// assertThat(id).isNotNull();
// assertThat(amount).isEqualTo(250.00);
    }

    // @Test
    // void shouldReturnLocationHeader() {
    // CashCard newCashCard = new CashCard(null, 250.00);
    // ResponseEntity<Void> createResponse =
    // restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
    // URI location = createResponse.getHeaders().getLocation();
    // ResponseEntity<String> resp = restTemplate.getForEntity(location,
    // String.class);
    // assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    // }
}
