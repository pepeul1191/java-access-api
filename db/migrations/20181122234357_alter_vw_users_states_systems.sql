-- migrate:up

DROP VIEW IF EXISTS vw_users_states_systems;

CREATE VIEW vw_users_states_systems AS SELECT 
  US.id, S.id AS system_id, S.name AS system, UE.id AS user_state_id, UE.name AS state, U.id AS user_id, U.user AS user, U.pass AS pass, U.email  
  FROM users_systems US 
  INNER JOIN users U ON U.id = US.user_id 
  INNER JOIN systems S ON S.id = US.system_id 
  INNER JOIN user_states UE ON U.user_state_id = UE.id  
  LIMIT 2000;

-- migrate:down

DROP VIEW IF EXISTS vw_users_states_systems;
