package com.exam.pricing.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.pricing.dto.OrderPriceInput;
import com.exam.pricing.facade.PricingFacade;
import com.exam.pricing.utils.PricingConstants;
import com.exam.pricing.utils.RestResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/price")
public class PricingController {

        @Autowired
        private PricingFacade pricingFacade;

        Logger logger = Logger.getLogger(PricingController.class.getName());

        @PostMapping(value = "/order-price")
        public ResponseEntity<RestResponse> computeOrderPrice(
                        @Validated @RequestBody final OrderPriceInput orderPriceInput,
                        final HttpServletRequest request) {
                logger.log(Level.FINE,
                                "Inside PricingEngineController.computeOrderPrice(), orderId is {}",
                                orderPriceInput.getOrderId());
                return new ResponseEntity<>(
                                new RestResponse(Boolean.TRUE,
                                                PricingConstants.RESPONSE_MESSAGE_ORDER_PRICE_RETRIEVED_SUCCESS,
                                                pricingFacade.computeOrderPrice(orderPriceInput)),
                                HttpStatus.OK);
        }
}
