Feature: Validate User
  Validate user

  Scenario: Validate user
    Given Generar petición HTTP "user_system/validate" con headers
    Given Crear POST data user-validate sin errores
    When Ejecutar petición HTTP Body Data
    Then Se debe obtener un status code success 200