package emt.emtau.web.rest;

import emt.emtau.model.Discount;
import emt.emtau.model.dto.DiscountDto;
import emt.emtau.service.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    //zavisnosti
    private final DiscountService discountService;

    public DiscountRestController(DiscountService discountService) {
        this.discountService = discountService;
    }

    //read
    //@RequestMapping(method = RequestMethod.GET) iliiii
    @GetMapping
    public List<Discount> findAll(){
        return this.discountService.findAll();
    }

    @GetMapping("/pagination")
    public List<Discount> findAllWithPagination(Pageable pageable){
        return this.discountService.findAllWithPagination(pageable).getContent();
    }

    /*
        so path variable
          http://localhost:9090/api/discounts/3
        ili so query request
          http://localhost:9090/api/discounts?id=3
    */
    @GetMapping("/{id}")
    public ResponseEntity<Discount> findById(@PathVariable Long id){
        return this.discountService.findById(id)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //create, post requests
    @PostMapping("/add")
    public ResponseEntity<Discount> save(@RequestBody DiscountDto discountDto){
        return this.discountService.save(discountDto)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //update
    @PutMapping("/edit/{id}")
    public ResponseEntity<Discount> edit(@PathVariable Long id, @RequestBody DiscountDto discountDto){
        return this.discountService.edit(id, discountDto)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.discountService.deleteById(id);
        //ako e izbrisano se e ok
        if (this.discountService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/assign/{id}")
    //assign
    public ResponseEntity<Discount> assignDiscount(@PathVariable Long id,
                                                   @RequestParam String username){
        return this.discountService.assignDiscount(username, id)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }
}
