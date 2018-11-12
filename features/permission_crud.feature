Feature: CRUD Permission
  Crear, Editar y Eliminar permission

  Scenario: Crear permission
    Given Generar petición HTTP "permission/save" con headers
    Given Crear POST data crear-permission
    When Ejecutar petición HTTP Body Data
    Then Se debe obtener un status code success 200
    Then Se debe obtener el id generado
  
  Scenario: Editar permission
    Given Generar petición HTTP "permission/save" con headers
    Given Crear POST data editar-permission
    When Ejecutar petición HTTP Body Data
    Then Se debe obtener un status code success 200
    Then No se debe obtener el id generado

  Scenario: Eliminar permission
    Given Generar petición HTTP "permission/save" con headers
    Given Crear POST data eliminar-permission
    When Ejecutar petición HTTP Body Data
    Then Se debe obtener un status code success 200
    Then No se debe obtener el id generado