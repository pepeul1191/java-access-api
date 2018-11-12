Feature: Udate User State
  Udate User State

  Scenario: Update user state
    Given Generar petición HTTP "user/update_state" con headers
    Given Crear POST data user-update-state sin errores
    When Ejecutar petición HTTP Form Data
    Then Se debe obtener un status code success 200