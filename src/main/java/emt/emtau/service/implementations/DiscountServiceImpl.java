package emt.emtau.service.implementations;

import emt.emtau.model.Discount;
import emt.emtau.model.User;
import emt.emtau.model.dto.DiscountDto;
import emt.emtau.model.exceptions.DiscountNotFoundException;
import emt.emtau.model.exceptions.UserNotFoundException;
import emt.emtau.repository.jpa.DiscountRepository;
import emt.emtau.repository.jpa.UserRepository;
import emt.emtau.service.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl  implements DiscountService {

    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Discount> findAll() {
        return this.discountRepository.findAll();
    }

    //todo
    @Override
    public Page<Discount> findAllWithPagination(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return this.discountRepository.findById(id);
    }

    @Override
    public Optional<Discount> save(DiscountDto discountDto) {
        return Optional.of(this.discountRepository.save(new Discount(discountDto.getValidUntil())));
    }

    @Override
    public Optional<Discount> edit(Long id, DiscountDto discountDto) {
        //dali postoi takov discount
        Discount discount = this.discountRepository.findById(id)
                .orElseThrow(() -> new DiscountNotFoundException(id));

        discount.setValidUntil(discountDto.getValidUntil());
        return Optional.of(this.discountRepository.save(new Discount(discountDto.getValidUntil())));
    }

    @Override
    public void deleteById(Long id) {
        this.discountRepository.deleteById(id);
    }

    @Override
    public Optional<Discount> assignDiscount(String username, Long discountId) {
        //find user
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        //find discount
        Discount discount = this.discountRepository.findById(discountId)
                .orElseThrow(() -> new DiscountNotFoundException(discountId));

        //zemi gi korisnicite i dodaj go nasiot noviot
        discount.getUsers().add(user);
        return Optional.of(this.discountRepository.save(discount));
    }
}
