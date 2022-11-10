package com.example.messages.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessagesApiController {
    private final List<String> messages = new ArrayList<>();

    @GetMapping("messages")
    public ResponseEntity<List<String>> getMessages(@RequestParam(value = "start", required = false) String start) {
        if (start == null)
            return ResponseEntity.ok(messages);

        List<String> filteredMessages = new ArrayList<>();

        for (String message: messages) {
            if (message.startsWith(start))
                filteredMessages.add(message);
        }

        return ResponseEntity.ok(filteredMessages);
    }

    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(@RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("messages/{index}")
    public ResponseEntity<String> getMessage(@PathVariable("index") Integer index) {
        return ResponseEntity.ok(messages.get(index));
    }

    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer index) {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("messages/{index}")
    public ResponseEntity<Void> updateMessage(@PathVariable("index") Integer i, @RequestBody String message) {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("messages/search/{text}")
    public ResponseEntity<Integer> searchMessage(@PathVariable("text") String text) {
        for (int i = 0; i < messages.size(); ++i) {
            if (messages.get(i).contains(text))
                return ResponseEntity.ok(i);
        }

        return ResponseEntity.ok(-1);
    }

    @GetMapping("messages/count")
    public ResponseEntity<Integer> messagesCount() {
        return ResponseEntity.ok(messages.size());
    }

    @PostMapping("messages/{index}/create")
    public ResponseEntity<Void> addMessageWithIndex(@RequestBody String text, @PathVariable("index") Integer index) {
        messages.add(index, text);

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<Void> deleteMessagesWithText(@PathVariable("text") String text) {
        CollectionUtils.filter(messages, m -> !m.contains(text));

        return ResponseEntity.noContent().build();
    }
}