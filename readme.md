# User Microservice

## Endpoints

### POST /sign-up

Recibe los datos del usuario por el body, lo da de alta, genera un token y lo devuelve.

### GET /login

Recibe el token por el header de authorization, si es válido busca el usuario y devuelve los datos.

