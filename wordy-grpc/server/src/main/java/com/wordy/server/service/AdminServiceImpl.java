package com.wordy.server.service;

import com.wordy.proto.AdminServiceGrpc;
import com.wordy.proto.Player;
import com.wordy.proto.PlayerId;
import com.wordy.proto.SearchQuery;
import com.wordy.proto.PlayerList;
import com.wordy.proto.Config;
import com.wordy.proto.Status;
import com.wordy.proto.Empty;
import com.wordy.server.dao.UserDAO;
import com.wordy.server.dao.ConfigDAO;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * gRPC service implementation for admin operations.
 * Handles player management and game configuration updates.
 */
public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {
    
    private final UserDAO userDAO = new UserDAO();
    private final ConfigDAO configDAO = new ConfigDAO();

    @Override
    public void createPlayer(Player request, StreamObserver<Status> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            // Check if player already exists
            if (userDAO.userExists(username)) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Player " + username + " already exists")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Create new player with default role "player"
            boolean created = userDAO.createUser(username, password, "player");
            
            if (created) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Player " + username + " created successfully")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to create player " + username)
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error creating player: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void updatePlayer(Player request, StreamObserver<Status> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            // Check if player exists
            if (!userDAO.userExists(username)) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Player " + username + " does not exist")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Update player (password and role)
            boolean updated = userDAO.updateUser(username, password, "player");
            
            if (updated) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Player " + username + " updated successfully")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to update player " + username)
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error updating player: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deletePlayer(PlayerId request, StreamObserver<Status> responseObserver) {
        // PlayerId only has id field, but we need username
        // For now, we'll return an error since we can't delete without username
        // TODO: Modify proto to use username instead of id, or add username field

        responseObserver.onNext(Status.newBuilder()
                .setSuccess(false)
                .setMessage("Delete by ID not supported. Please use username.")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void searchPlayer(SearchQuery request, StreamObserver<PlayerList> responseObserver) {
        // TODO: Implement player search functionality
        responseObserver.onNext(PlayerList.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateConfig(Config request, StreamObserver<Status> responseObserver) {
        try {
            int waitTime = request.getWaitTime();
            int roundDuration = request.getRoundDuration();

            // Validate values
            if (waitTime < 0 || roundDuration < 0) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Invalid config values: wait_time and round_duration must be non-negative")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // Update configuration
            boolean updated = configDAO.updateConfig("wait_time", String.valueOf(waitTime))
                    && configDAO.updateConfig("round_duration", String.valueOf(roundDuration));

            if (updated) {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(true)
                        .setMessage("Configuration updated: wait_time=" + waitTime + "s, round_duration=" + roundDuration + "s")
                        .build());
            } else {
                responseObserver.onNext(Status.newBuilder()
                        .setSuccess(false)
                        .setMessage("Failed to update configuration")
                        .build());
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onNext(Status.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error updating config: " + e.getMessage())
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getConfig(Empty request, StreamObserver<Config> responseObserver) {
        try {
            int waitTime = configDAO.getConfigAsInt("wait_time", 10);
            int roundDuration = configDAO.getConfigAsInt("round_duration", 30);

            responseObserver.onNext(Config.newBuilder()
                    .setWaitTime(waitTime)
                    .setRoundDuration(roundDuration)
                    .build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(new Exception("Error fetching config: " + e.getMessage()));
        }
    }
}
