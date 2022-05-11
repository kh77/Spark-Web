### POST Employee Request


    curl --location --request POST 'localhost:4567/employees' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name":"talha",
    "department":"IT"
    }'


### Get Employee Ids
    http://localhost:4567/employees


### Get Employee by Id
    http://localhost:4567/employees/3