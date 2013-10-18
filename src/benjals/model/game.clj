(ns benjals.model.game
  (:require [clojure.java.jdbc :as sql]
            [benjals.model.entity :as entity]))

(defn get-players [teamId id]
  (sql/with-query-results results
    ["select users.*, users_teams.alternate, game_attendances.attending from users
      inner join users_teams
      on users.id = users_teams.user_id
      and users_teams.team_id = ?
      left join game_attendances
      on users.id = game_attendances.user_id
      and game_attendances.game_id = ?" teamId id]
    (into [] results)))

(defn get-all [team-id]
  (sql/with-query-results results
    ["select * from games where team_id = ? order by id desc" team-id]
    (into [] results)))

(defn get-by-id [game-id]
  (entity/get-by-id "games" game-id))

(defn create [teamId game]
  (sql/do-prepared-return-keys "insert into games (team_id, start_time) values (?, to_timestamp(?))"
    [teamId (game "start_time")]))