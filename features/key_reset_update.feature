Feature: Change Reset Key
  Change Reset Key

  Scenario: Change Reset user
    Given Generar petición HTTP "key/reset/update_by_user_id" con headers
    Given Crear POST data key-reset-update sin errores
    When Ejecutar petición HTTP Form Data
    Then Se debe obtener un status code success 200