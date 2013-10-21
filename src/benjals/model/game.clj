(ns benjals.model.game
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-players [db teamId id]
  (sql/query db
    ["select users.*, users_teams.alternate, game_attendances.attending from users
      inner join users_teams
      on users.id = users_teams.user_id
      and users_teams.team_id = ?
      left join game_attendances
      on users.id = game_attendances.user_id
      and game_attendances.game_id = ?" teamId id]))

(defn get-all [db team-id]
  (sql/query db
    ["select * from games where team_id = ? order by id desc" team-id]))

(defn get-by-id [db game-id]
  (entity/get-by-id db "games" game-id))

(defn create [db teamId game]
  (sql/db-do-prepared-return-keys db
    "insert into games (team_id, start_time) values (?, to_timestamp(?))" [teamId (game "start_time")]))