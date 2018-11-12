Given("Crear POST data crear-permission") do
  @data = {
    :nuevos => [
      {
        :id => 'tablaDeparamento_481',
        :name => 'Deparamento N1',
      },
      {
        :id => 'tablaDeparamento_482',
        :name => 'Deparamento N2',
      },
    ],
    :editados => [],
    :eliminados => [],
    :extra => {
      :pais_id => 2
    }
  }
  @nuevos = 2
end

Given("Crear POST data editar-permission") do
  @data = {
    :nuevos => [],
    :editados => [
      {
        :id => 6,
        :name => 'Colombia XD',
      },
    ],
    :eliminados => [],
    :extra => {
      :pais_id => 2
    }
  }
  @nuevos = 0
end

Given("Crear POST data eliminar-permission") do
  @data = {
    :nuevos => [],
    :editados => [],
    :eliminados => [23,24,25,26],
    :extra => {
      :pais_id => 2
    }
  }
  @nuevos = 0
end