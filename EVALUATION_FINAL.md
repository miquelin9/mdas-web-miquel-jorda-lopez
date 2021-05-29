## Hace todos los puntos pedidos (40%)

#### El nombre del repositorio es el correcto (mdas-web-${nombre}_${apellido})

OK

#### Permite obtener los detalles de un pokemon vía endpoint (datos + número de veces que se ha marcado como favorito)

OK

#### Actualiza el contador de favoritos vía eventos

OK

#### ¿Se controlan las excepciones de dominio? Y si se lanza una excepción desde el dominio, ¿se traduce en infraestructura a un código HTTP?

OK

#### Tests unitarios

- En el caso de uso `FavouritePokemonAddedNotifierUseCaseTest` estás testeando mockeando los agregados y objetos, no es
  necesario.

#### Tests de aceptación

- El test `RabbitMqEventListenerIT` no está testeando el flujo de añadir un pokemon favorito, sino únicamente el recibir
  un evento con un pokemon, este se guarda.

#### Tests de integración

OK

**Puntuación: 35/40**

## Se aplican conceptos explicados (50%)

#### Separación correcta de capas (application, domain, infrastructure + BC/module/layer)

- Los eventos de dominio suelen llamarse `objectActionEvent`, no llevan la palabra message, ya que no es un mensaje en
  sí, sino que es un evento con información de lo que acaba de suceder en el sistema. Ejemplo: `FavouritePokemonAdded`
  o `FavouritePokemonAddedEvent`. Y el evento de dominio no es ninguna entidad.

- La responsabilidad de crear el evento y añadirlo la tiene el agregado, no el caso de uso. El caso de uso tiene la
  responsabilidad de publicar el evento.

- Estás implementando una especie de caché delegando esta responsabilidad en la capa de dominio. La idea es buena, pero
  esta responsabilidad de comprobar si existe en nuestra memoria el pokemon debería estar en infraestructura.

#### Aggregates + VOs

OK

#### No se trabajan con tipos primitivos en dominio

OK

#### Hay servicios de dominio

OK

#### Hay use cases en aplicación reutilizables

OK

#### Se aplica el patrón repositorio

OK

#### Se usan subscribers

OK. Aunque la solución actual no es muy escalable, ya que está orientada a que haya solo un evento en todo el sistema,
cuando una arquitectura basada en eventos, escucha y lanza muchos eventos. Siguiendo el ejemplo comentado en clase, la
anotación `RabbitListener` hubiera sido todo más legible y sencillo de implementar ;-)

#### Se lanzan eventos de dominio

A medias, ya que lo que se lanzan no son eventos de dominio pertenecientes al agregado, sino mensajes sin significado en
sí.

#### Se utilizan object mothers

- Están creados en el package main cuando es algo de uso exclusivo para tests.
- No se utilizan en todos los tests, únicamente se utilizan en los tests de integración del bounded context `users`.

**Puntuación: 40/50**

## Facilidad setup + README (10%)

#### El README contiene al menos los apartados "cómo ejecutar la aplicación", "cómo usar la aplicación"

OK

#### Es sencillo seguir el apartado "cómo ejecutar la aplicación"

OK

**Puntuación: 10/10**

## Extras

- Añadido una colección de postman con los endpoints a usar +5
- Añadida una cache que permite no andar pidiendo los datos de un pokemon a la poke-api continuamente +5

**Puntuación: +10**

## Observaciones

- Los `LOGGER.info` entiendo que los hayas usado para probar la aplicación, pero es un código que no debería
  entregarse (al igual que el código comentado). No aporta valor, pero si ruido.

**PUNTUACIÓN FINAL: 95/100**
