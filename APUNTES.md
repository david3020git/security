# Apuntes seguridad spring

La seguridad comienza al usuario hacer un request a nuestra aplicación

Lo primero que se jecuta es el filtro de autenticación este se encarga de validar el token necesario para spring
    `si el token no es valido devuelve un http response 403 `

Acontinuación hace una llamada a la clase UserDetailService para intetar obtener la inforación del usuario.
    `UserDetailService se encargara de extrar datos del token para buscar al usuario en base de datos`
luego retorna una respuesta valida si ha encontrado el usuario o negativa en caso de que no lo pueda encontrar
daremos una respuesta al usuario 

` En caso que el usuario exista procedemos a validar el token  con el usuario y sus datos`
`validaremos que el token sea correcto y pasamos al escenario`
 Security update securityContextHolder ` que seria autenticar alusuario y su token par el uso en nuestra aplicación`

## Comenzamos el projecto 
    * Creando la clase User
    * @Data -> nos crea los metodos principales 'setters', 'geters', 'constructure'
    * @Builder -> Me ayuda a crear el objeto de manera mas facil al usar el patron builder
    * @NoArgsContructor -> Builder necesita todos los argumentos por ello esta anotación
    * Implementamos la clase UserDetails de spring.yakarta asi que implementamos todos sus metodos
        Creamos una class enum Role para el uso en el metodo Collections
    * Creamos la interfaces para nuestra clase User
    * Pasamos a crear el filtro para la auenticacion

### Filtro de autenticación
    
    1 Creamos una clase JwtAtuthenticationFilter  y extendemos de OncePerRequestFilter 
        esto significa que cada solicitud request pase por este filtro
            JwtAuthenticationFilterD
    2 Creación del token de autenticación
        Parte sde un token
            Header -> tipo de algoritmo claims
            Payload -> datos 
            Signature -> Verificaión del token
