package com.app.playerservicejava.controller.chat;

import com.app.playerservicejava.model.CompareRequest;
import com.app.playerservicejava.service.chat.ChatClientService;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = "v1/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatClientService chatClientService;

    @PostMapping
    public @ResponseBody String chat() throws OllamaBaseException, IOException, InterruptedException {
        return chatClientService.chat();
    }

    @GetMapping("/list-models")
    public ResponseEntity<List<Model>> listModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        List<Model> models = chatClientService.listModels();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/player-summary/{playerId}")
    public ResponseEntity<String> playerSummary(@PathVariable String playerId) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        LOGGER.info("getting player summary: "+ playerId);
        String summary = chatClientService.generatePlayerSummary(playerId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/compare/{playerId1}/{playerId2}")
    public ResponseEntity<String> comparePlayers(@PathVariable String playerId1,@PathVariable String playerId2) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(chatClientService.comparePlayers(List.of(playerId1,playerId2)));
    }

    /****
     * curl -X POST http://localhost:8080/v1/chat/compare \
     * -H 'Content-Type application/json' \
     * -d '{"playerIds": ["aaronha01","aardsda01"]}'
     * @param request
     * @return
     */
    @PostMapping("/compareRequest")
    public ResponseEntity<String> comparePlayersRequest(@RequestBody CompareRequest request) throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(chatClientService.comparePlayers(request.getPlayerIds()));
    }

}
