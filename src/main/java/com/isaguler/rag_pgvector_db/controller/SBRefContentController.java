package com.isaguler.rag_pgvector_db.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SBRefContentController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    @Value("classpath:/prompts/sb-ref.st")
    private Resource sbPromptTemplate;

    public SBRefContentController(ChatClient.Builder chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient.build();
        this.vectorStore = vectorStore;
    }

    @GetMapping("/table-of-content")
    public String aboutRefDocTableOfContent(@RequestParam(value = "message", defaultValue = "Which section or sections in Spring Boot reference is about GraalVM?") String message) {
        PromptTemplate promptTemplate = new PromptTemplate(sbPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", message);
        promptParameters.put("documents", String.join("\n", findSimilarDocuments(message)));

        return chatClient.prompt(promptTemplate.create(promptParameters)).call().content();
    }

    private List<String> findSimilarDocuments(String message) {
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(3));
        return similarDocuments.stream().map(Document::getContent).toList();
    }

}
