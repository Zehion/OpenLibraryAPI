package com.nexify.openlibraryapi.repositories;

import com.nexify.openlibraryapi.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findByLanguageCode(String languageCode);  // Metodo para buscar idiomas por código de idioma
}

