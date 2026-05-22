package com.localchat.service;

import com.localchat.model.ChatMessage;
import com.localchat.model.ChatUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageStore {

    // Stores all messages for the public room
    // ConcurrentHashMap is thread-safe — multiple users sending at the same
    // time won't corrupt the list
    private final List<ChatMessage> messages =
        Collections.synchronizedList(new ArrayList<>());

    // Tracks currently connected users
    // Key = sessionId, Value = ChatUser object
    // This lets us look up a user instantly when they disconnect
    private final Map<String, ChatUser> activeUsers = new ConcurrentHashMap<>();

    // Hard cap on messages kept in memory
    // Prevents the list growing forever if the server runs for a long time
    private static final int MAX_MESSAGES = 500;

    // --- Message operations ---

    public void saveMessage(ChatMessage message) {
        messages.add(message);

        // If we've exceeded the cap, drop the oldest message
        // This keeps memory usage bounded no matter how long the server runs
        if (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
        }
    }

    public List<ChatMessage> getAllMessages() {
        // Return an unmodifiable snapshot so callers can't mutate the list
        return Collections.unmodifiableList(new ArrayList<>(messages));
    }

    // Wipes ALL messages — called when the last user leaves the room
    public void clearAllMessages() {
        messages.clear();
    }

    // --- User operations ---

    public void addUser(ChatUser user) {
        activeUsers.put(user.getSessionId(), user);
    }

    public ChatUser getUser(String sessionId) {
        return activeUsers.get(sessionId);
    }

    public void removeUser(String sessionId) {
        activeUsers.remove(sessionId);
    }

    public List<ChatUser> getActiveUsers() {
        return Collections.unmodifiableList(new ArrayList<>(activeUsers.values()));
    }

    public int getActiveUserCount() {
        return activeUsers.size();
    }

    // Called on disconnect — removes the user AND clears all messages
    // if they were the last person in the room
    public boolean removeUserAndCheckEmpty(String sessionId) {
        activeUsers.remove(sessionId);
        if (activeUsers.isEmpty()) {
            messages.clear();  // Privacy guarantee: wipe history when room empties
            return true;       // Signals to the caller the room is now empty
        }
        return false;
    }
}