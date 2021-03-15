run-db:
	docker run --name warehouse_db -p 9095:5432 -e POSTGRES_USER=userdev -e POSTGRES_PASSWORD=asd123 -e POSTGRES_DB=warehouse_db -d postgres:13.2

build:
	docker build --network=host -t warehouse_app:0.1.0 .

run:
	docker run  --env-file .env -p 8080:8080 warehouse_app:0.1.0

set-up-and-run:
	make run-db build run
