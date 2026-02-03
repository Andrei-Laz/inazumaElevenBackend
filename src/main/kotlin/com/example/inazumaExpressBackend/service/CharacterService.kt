package com.example.inazumaExpressBackend.service

import com.example.inazumaExpressBackend.model.Character
import com.example.inazumaExpressBackend.repository.CharacterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CharacterService {

    @Autowired
    private lateinit var characterRepository: CharacterRepository

    fun getAllCharacters(): List<Character> = characterRepository.findAll()

    fun getCharacterById(id: Int): Character? = characterRepository.findById(id).orElse(null)

    fun saveCharacter(character: Character): Character = characterRepository.save(character)

    fun updateCharacter(id: Int, updatedCharacter: Character): Character? {
        if (characterRepository.existsById(id)) {
            updatedCharacter.characterId = id
            return characterRepository.save(updatedCharacter)
        }
        return null
    }

    fun deleteCharacter(id: Int): Boolean {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id)
            return true
        }
        return false
    }

    fun searchByNickname(nickname: String): List<Character> = characterRepository.findByNickname(nickname)
}