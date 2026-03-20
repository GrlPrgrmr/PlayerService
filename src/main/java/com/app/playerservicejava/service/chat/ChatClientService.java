package com.app.playerservicejava.service.chat;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.repository.PlayerRepository;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.types.OllamaModelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class ChatClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClientService.class);

    @Autowired
    private OllamaAPI ollamaAPI;

    @Autowired
    private PlayerRepository playerRepository;

    private static final String MODEL = OllamaModelType.TINYLLAMA;

    public List<Model> listModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        List<Model> models = ollamaAPI.listModels();
        return models;
    }

    public String chat() throws OllamaBaseException, IOException, InterruptedException {

        // https://ollama4j.github.io/ollama4j/intro
        PromptBuilder promptBuilder =
                new PromptBuilder()
                        .addLine("Recite a haiku about recursion.");

        boolean raw = false;
        OllamaResult response = ollamaAPI.generate(MODEL, promptBuilder.build(), raw, new OptionsBuilder().build());
        return response.getResponse();
    }

    //fetches player from db builds a prompt and asks llama to write a bio
    public String generatePlayerSummary(String playerId) throws OllamaBaseException, IOException, InterruptedException {
        Optional<Player> player = playerRepository.findById(playerId);
        if(player.isEmpty()){
            return "Player does not exist: "+ playerId;
        }
        Player p = player.get();
        String prompt = new PromptBuilder()
                .addLine("You are a professional sports analyst.")
                .addLine("")
                .addLine("Write a concise player profile (3-4 sentences).")
                .addLine("")
                .addLine("Player Details:")
                .addLine("Name: "+ p.getFirstName()+ " " + p.getLastName()+"("+p.getGivenName()+")")
                .addLine("Born: "+ p.getBirthYear() + "-" + p.getBirthMonth()+"-"+p.getBirthDay())
                .addLine("BirthPlace: "+p.getBirthCity() + "-"+p.getBirthState() + "-"+p.getBirthCountry())
                .addLine("Height: "+p.getHeight())
                .addLine("Debut: "+p.getDebut()+", Final Game: "+p.getFinalGame())
                .addLine("")
                .addLine("Write the summary now:")
                .build();

        OllamaResult response = ollamaAPI.generate(MODEL, prompt, false, new OptionsBuilder().build());
        return response.getResponse();
    }

    public String comparePlayers(List<String> playerIds) throws OllamaBaseException, IOException, InterruptedException {
        if (playerIds == null || playerIds.size() < 2) {
            return "Please provide at least 2 player IDs to compare";
        }

        PromptBuilder prompt = new PromptBuilder()
                .addLine("You are a professional sports analyst.")
                .addLine("")
                .addLine("Compare the following players in 4-6 concise sentences.")
                .addLine("Focus on similarities, differences and interesting observations.")
                .addLine("")
                .addLine("Player Details:");

        for (String id : playerIds) {
            Optional<Player> opt = playerRepository.findById(id);
            if (opt.isEmpty()) {
                return "Player does not exist: " + id;
            }

            Player p = opt.get();
            prompt.addLine("")
                    .addLine("Name: " + p.getFirstName() + " " + p.getLastName() +
                            " (" + p.getGivenName() + ")")
                    .addLine("Born: " + p.getBirthYear() + "-" +
                            p.getBirthMonth() + "-" + p.getBirthDay())
                    .addLine("Height: " + p.getHeight())
                    .addLine("Bats: " + p.getBats())
                    .addLine("Debut: " + p.getDebut() +
                            ", Final Game: " + p.getFinalGame())
                    .addLine("")
                    .addLine("Write the summary now:");

        }
        OllamaResult response = ollamaAPI.generate(MODEL, prompt.build(), false, new OptionsBuilder().build());
        return response.getResponse();
    }

}
