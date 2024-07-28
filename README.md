Written as spring boot web application.

API Documentation:

POST - http://localhost:8080/v1/price/order-price
Postman collection JSON file is attached. Check file samplerequest.json

Description: Incoming request should have below fields as part of mocking
              ---- userRole as [Employee / Affiliate / Customer] (Ignorecase) to apply the percentage discounts.
              ---- userCreatedOn field as LocalDate(YYYY-MM-DD) to check if the customer is eligible for percentage discount
              ---- inside items array, category field is used to check if product is grocery or not. 
                    If value is passed as Grocery(Ignorecase) no percentage discounts will be applied

              Response is an order structure, where discounts applied to each product can be seen in DiscountInfo and PriceInfo sections
              Net Payable amount is given in "data.priceInfo.netPrice"


