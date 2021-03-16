# Warehouse Manager

Service for managing the warehouse

### Prerequisites:

- Make
- Docker

### Run locally

From the project root directory, run: 
* Make set-up-and-run

Once application is up and running you can reach the api at:
* http://localhost:8080

If changes are made to project, to redeploy:
* Make build run

### API

In postman_collection.json file there are example requests and responses for all the available web services with 
descriptions. It can be imported to Postman (or it can be read as documentation).

List of services:

- GET articles
Get all articles

- GET articles/{id}
Get article by id

- POST articles
Create article if name does not exist or add stock to current inventory if article with given name is found

- GET products
Get all products

- GET products/{id}
Get product by id

- DELETE products/{id}
Delete product by id

- POST products
Create products

- POST products/{id}/sale?quantity={}
'Sells' a product in the quantity specified. In practical terms, removes the relevant articles from the warehouse

- POST products/{id}/available_quantity
Returns the maximum quantity for a product based on the available articles in the warehouse

- POST products/availability
Checks if a whole order(a collection of products in the quantities wanted) is possible based on the available stock


### Short project description

The project has two components, a Postgres database that represents the warehouse and a SpringBoot application that is 
the backend service which provides access to the warehouse. 

I tried to implement all the necessary web services to support key operations for a warehouse (e.g. put items 
in the bucket for checkout, make a order, etc) given the description and the time constraints.

### Additional notes

Some things are left unfinished or can be considered unfinished for an application headed to prod:

- Unit Tests: I created unit tests for some methods to demonstrate my way of unit testing but service layer should
 have better coverage percentage.
 
- Infra: k8s configuration. Passwords are in plain text as well, a secrets manager should be used.

- Integration Tests












