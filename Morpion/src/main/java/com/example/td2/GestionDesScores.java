    package com.example.td2;

    import java.util.HashMap;
    import java.util.Map;

    public class GestionDesScores {
        private final Map<String, Integer> scores;

        public GestionDesScores() {
            this.scores = new HashMap<>();
        }

        public void ajoutVictoire(String playerName) {

            if (scores.containsKey(playerName)) {
                scores.put(playerName, scores.get(playerName) + 1);
            } else {
                scores.put(playerName, 1);
            }
        }

        public int getScore(String playerName) {
            return scores.getOrDefault(playerName, 0);
        }
    }
