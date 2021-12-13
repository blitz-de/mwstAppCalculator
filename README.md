# MwstCalculator
Run the first services in order:

First, run:

1. Discovery Service
2. Api-Gateway
3. Other Services

Second,
# POST request 
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

Third, 
# GET request 
Get a product with a specific ID using the following request link: 
`http://localhost:8765/api/products/1`

