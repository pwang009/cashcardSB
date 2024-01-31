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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CashCardHttpTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturn200IfRestTemplateIsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkIdFieldIsNotNull() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        DocumentContext context = JsonPath.parse(response.getBody());
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
    }

    @Test
    void checkCashCardReturnHardCodedValue() {
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/100", String.class);

        DocumentContext context = JsonPath.parse(response.getBody());
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(100);
    }
}
