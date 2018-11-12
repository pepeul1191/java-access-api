Feature: Validate Activation Key
  Validate Activation Key

  Scenario: Validate user
    Given Generar petición HTTP "key/activation/validate" con headers
    Given Crear POST data activation-key-validate sin errores
    When Ejecutar petición HTTP Form Data
    Then Se debe obtener un status code success 200