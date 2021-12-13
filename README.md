# MwstCalculator
Run the first services in order:

First, run:

1. Discovery Service
2. Api-Gateway
3. Other Services

Second,
# POST request through http://localhost:8765/api/products/add:
In POSTMAN try adding the following body, example:

`
<br/>
{\
    "name":"generator",<br/>
    "price": "1000",<br/>
    "location": "Berlin",<br/>
    "description": "light your whole house"<br/>
}<br/>
`

Third, 
# GET request (Product) http://localhost:8765/api/products/1

