package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashcards")
class CashCardController {

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById() {

        CashCard card= new CashCard(100L, 123.45);

        return ResponseEntity.ok(card);
    }
}