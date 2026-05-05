package com.wordy.server.service;

import com.wordy.proto.LeaderboardServiceGrpc;
import com.wordy.proto.Empty;
import com.wordy.proto.LeaderboardResponse;
import com.wordy.proto.PlayerStats;
import com.wordy.proto.WordRecord;
import com.wordy.server.dao.UserDAO;
import com.wordy.server.dao.WordDAO;
import io.grpc.stub.StreamObserver;

import java.util.List;

/**
 * gRPC service implementation for leaderboard operations.
 * Fetches top players and longest words from database.
 */
public class LeaderboardServiceImpl extends LeaderboardServiceGrpc.LeaderboardServiceImplBase {
    
    private final UserDAO userDAO = new UserDAO();
    private final WordDAO wordDAO = new WordDAO();

    @Override
    public void getLeaderboard(Empty request, StreamObserver<LeaderboardResponse> responseObserver) {
        try {
            // Fetch top 5 players by wins
            List<String[]> topPlayers = userDAO.getTopPlayersByWins(5);
            
            // Fetch top 5 longest words
            List<String[]> longestWords = wordDAO.getTopLongestWords(5);

            LeaderboardResponse.Builder builder = LeaderboardResponse.newBuilder();

            // Add top players
            if (topPlayers != null) {
                for (String[] player : topPlayers) {
                    if (player.length >= 2) {
                        builder.addTopPlayers(PlayerStats.newBuilder()
                                .setUsername(player[0])
                                .setWins(Integer.parseInt(player[1]))
                                .build());
                    }
                }
            }

            // Add longest words
            if (longestWords != null) {
                for (String[] word : longestWords) {
                    if (word.length >= 3) {
                        builder.addLongestWords(WordRecord.newBuilder()
                                .setUsername(word[0])
                                .setWord(word[1])
                                .setLength(Integer.parseInt(word[2]))
                                .build());
                    }
                }
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Exception("Error fetching leaderboard: " + e.getMessage()));
        }
    }
}
