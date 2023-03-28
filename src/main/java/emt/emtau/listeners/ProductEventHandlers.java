package emt.emtau.listeners;

import emt.emtau.model.event.ProductCreatedEvent;
import emt.emtau.service.ProductService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandlers {

    private final ProductService productService;

    public ProductEventHandlers(ProductService productService) {
        this.productService = productService;
    }

    @EventListener
    public void onProductCreated(ProductCreatedEvent event){
        //refresh
        this.productService.refreshMaterializedView();
    }
}
