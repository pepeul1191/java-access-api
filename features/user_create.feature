Feature: Crear User
  Crear user

  Scenario: Crear user
    Given Generar petición HTTP "user/create" con headers
    Given Crear POST data create-user sin errores
    When Ejecutar petición HTTP Body Data
    Then Se debe obtener un status code success 200