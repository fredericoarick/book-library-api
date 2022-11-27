# Endpoints

## Cliente

### Cadastrar um cliente

`POST /customer`

Permite cadastrar um novo cliente

Exemplo de cURL:
```
curl --location --request POST 'localhost:8080/customer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf": "11122233344",
    "name": "João da Silva",
    "birthDate": "2000-01-01"
}'
```

Exemplo de payload de response:

```
{
    "cpf": "11122233344",
    "name": "João da Silva",
    "birthDate": "2000-01-01"
}
```

### Listar clientes

`GET /customer`

Permite listar clientes cadastrados

Exemplo de cURL:

```
curl --location --request GET 'localhost:8080/customer'
```

Exemplo de payload de response:

```
[
    {
        "cpf": "11122233344",
        "name": "João da Silva",
        "birthDate": "2000-01-01"
    },
    {
        "cpf": "55566677788",
        "name": "Maria da Silva",
        "birthDate": "2001-02-02"
    },
]
```

### Obter dados de um cliente por CPF

`GET /customer/{cpf}`

Permite obter dados de um cliente em específico

Exemplo de cURL:

```
curl --location --request GET 'localhost:8080/customer/11122233344'
```

Exemplo de payload de response:

```
{
    "cpf": "11122233344",
    "name": "João da Silva",
    "birthDate": "2000-01-01"
}
```

## Livro 

### Cadastrar um livro

`POST /book`

Permite cadastrar um novo livro

Exemplo de cURL:
```
curl --location --request POST 'localhost:8080/book' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Hobbit",
    "author": "Tolkien",
    "publicationDate": "1937-09-21"
}'
```

Exemplo de payload de response:

```
{
    "id": 1,
    "title": "Hobbit",
    "author": "Tolkien",
    "publicationDate": "1937-09-21",
    "bookCopies": null
}
```

### Cadastrar um novo exemplar de livro

`POST /book/{bookId}/copy`

Permite cadastrar um novo exemplar de um livro

Exemplo de cURL:
```
curl --location --request POST 'localhost:8080/book/1/copy'
```

Exemplo de payload de response:

```
{
    "id": 1,
    "available": true
}
```

### Listar livros

`GET /book`

Permite listar todos os livros e seus exemplares

Exemplo de cURL:
```
curl --location --request GET 'localhost:8080/book'
```

Exemplo de payload de response:

```
[
    {
        "id": 1,
        "title": "Hobbit",
        "author": "Tolkien",
        "publicationDate": "1937-09-21",
        "bookCopies": [
            {
                "id": 1,
                "available": true
            }
        ]
    }
]
```

### Obter dados de um livro

`GET /book/{id}`

Permite obter dados de um livro em específico pelo ID

Exemplo de cURL:

```
curl --location --request GET 'localhost:8080/book/1'
```

Exemplo de payload de response:

```
{
    "id": 1,
    "title": "Hobbit",
    "author": "Tolkien",
    "publicationDate": "1937-09-21",
    "bookCopies": [
        {
            "id": 1,
            "available": true
        }
    ]
}
```

## Retirada de livros

### Retirar um livro

`POST /withdrawal`

Permite registrar a retirada de uma cópia de um livro
* Não é permitida e retirada de cópias indisponíveis
* Ao realizar a retirada, a cópia em específico é marcada como indisponível

Exemplo de cURL:
```
curl --location --request POST 'localhost:8080/withdrawal' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerCpf": "11122233344",
    "bookCopyId": 1
}'
```

Exemplo de payload de response:

```
{
    "id": 1,
    "withdrawDate": "2022-11-27T19:08:20.7141398",
    "returnLimitDate": "2022-12-11",
    "customer": {
        "cpf": "11122233344",
        "name": "João da Silva",
        "birthDate": "2000-01-01"
    },
    "bookCopy": {
        "id": 1,
        "available": true
    }
}
```

### Renovar retirada de um livro

`PUT /withdrawal/{id}`

Permite renovar o prazo de entrega de um livro por mais duas semanas
* Não é possível renovar prazo para livros com devolução atrasada
* Não é possível renovar o prazo para um valor superior de 2 meses em relação a data de retirada

Exemplo de cURL:
```
curl --location --request PUT 'localhost:8080/withdrawal/1'
```

Exemplo de payload de response:

```
{
    "id": 1,
    "withdrawDate": "2022-11-27T19:08:20.71414",
    "returnLimitDate": "2022-12-25",
    "customer": {
        "cpf": "11122233344",
        "name": "João da Silva",
        "birthDate": "2000-01-01"
    },
    "bookCopy": {
        "id": 1,
        "available": false
    }
}
```

### Devolver livro

`DELETE withdrawal/{id}`

Permite registrar a devolução de um livro
* Após devolução, cópia de livro é marcada disponível

Exemplo de cURL:

```
curl --location --request DELETE 'localhost:8080/withdrawal/1'
```

### Listar retiradas ativas

`GET withdrawal/active?cpf={cpf}`

Permite listar retiradas de livro ativas. 
* Passando `cpf`, é listado para um determinado cliente
* Omitindo `cpf`, é listado todas as retiradas

Exemplo de cURL:

```
curl --location --request GET 'localhost:8080/withdrawal/active?cpf=11122233344'
```

Exemplo de payload de response:

```
[
    {
        "withdrawalId": 1,
        "title": "Hobbit",
        "author": "Tolkien",
        "bookCopyId": 1,
        "bookId": 1,
        "customer_cpf": "11122233344",
        "withdrawDate": "2022-11-27T19:08:20.71414",
        "returnLimitDate": "2022-12-25",
        "lateToReturn": false
    }
]
```

## Histórico

### Listar hostórico de retiradas de um cliente

`GET withdrawal/returned?cpf={cpf}`

Permite listar retiradas de livro ativas. 

Exemplo de cURL:

```
curl --location --request GET 'localhost:8080/withdrawal/returned?cpf=11122233344'
```

Exemplo de payload de response:

```
[
    {
        "withdrawalId": 1,
        "title": "Hobbit",
        "author": "Tolkien",
        "bookCopyId": 1,
        "bookId": 1,
        "customer_cpf": "11122233344",
        "withdrawDate": "2022-11-27T19:08:20.71414",
        "returnDate": "2022-11-27T19:18:34.719979",
        "returnLimitDate": "2022-12-25",
        "returnedLate": false
    }
]
```
