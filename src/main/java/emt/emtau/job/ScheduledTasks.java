package emt.emtau.job;

import emt.emtau.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ProductService productService;

    public ScheduledTasks(ProductService productService) {
        this.productService = productService;
    }

    //fixedDelay = 5000ms = 5s znaci na sekoi 5sekundi se refreshira
    @Scheduled(fixedDelay = 5000)
    public void refreshMaterializedView() {
        //iskomentirano pri testiranje za listeners
//        this.productService.refreshMaterializedView();
    }
}
