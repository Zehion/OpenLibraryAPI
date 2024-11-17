package com.nexify.openlibraryapi.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TransactionalHelper {

    private final ApiService apiService;

    public TransactionalHelper(ApiService apiService) {
        this.apiService = apiService;
    }

    @Transactional
    public String fetchBooksByAuthor(String authorName) {
        return apiService.fetchBooksByAuthorInternal(authorName);
    }

    @Transactional
    public void saveJsonData(String jsonData) {
        apiService.saveJsonDataInternal(jsonData);
    }
}

