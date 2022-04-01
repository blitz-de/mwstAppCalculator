# MwstCalculator

At the beginning, run the following services in order:

1. Discovery Service
2. Api-Gateway
3. Other Services


# Important INFO
Every request will go through the API-Gateway where all other services are added. The Port to the Gateway is `:8765`

Second, 

# Swagger
Swagger will help with having a very good visualisation of which endpoints are available in which service and what each endpoint does. 

The swaggerdoc will run in different ports according to which service is required. 

The url to swagger is:
`http://localhost:8084/swagger-ui/index.html#/`

Available ports are:
* 8081 -> main app
* 8082 -> calculator app
* 8084 -> storage app


Third,

# MainApplication Service
## POST request 
Add a new product using the following request: `http://localhost:8765/api/products/add:` <br/>
In POSTMAN try adding the following body to add a specific product, example:

`

{

    "name":"generator",
    
    "price": "1000",
    
    "location": "Berlin",
    
    "description": "light your whole house"
}

`

Foruth, 
## GET request 
Get a product with a specific ID using the following request link: 
`http://localhost:8765/api/products/1`

# Calculator Service
# GET request
To get the VAT (Mehrwertsteuer) of a specific product using its ID. <br/>
For example, get the MwtSt of product 1: `http://localhost:8765/api/calculator/product/1`
