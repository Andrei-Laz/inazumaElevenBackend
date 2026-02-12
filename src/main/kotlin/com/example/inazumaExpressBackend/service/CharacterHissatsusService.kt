package com.example.inazumaExpressBackend.service

import com.example.inazumaExpressBackend.model.CharacterHissatsuId
import com.example.inazumaExpressBackend.model.CharacterHissatsus
import com.example.inazumaExpressBackend.model.Hissatsu
import com.example.inazumaExpressBackend.repository.CharacterHissatsusRepository
import com.example.inazumaExpressBackend.repository.CharacterRepository
import com.example.inazumaExpressBackend.repository.HissatsuRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CharacterHissatsusService(
    private val repository: CharacterHissatsusRepository,
    private val characterRepo: CharacterRepository,
    private val hissatsuRepo: HissatsuRepository
) {

    @Transactional
    fun assignHissatsuToCharacter(characterId: Int, hissatsuId: Int): CharacterHissatsus {
        if (!characterRepo.existsById(characterId))
            throw RuntimeException("Character not found")
        if (!hissatsuRepo.existsById(hissatsuId))
            throw RuntimeException("Hissatsu not found")

        if (repository.existsByCharacterIdAndHissatsuId(characterId, hissatsuId))
            throw RuntimeException("Hissatsu already assigned to this character")

        val character = characterRepo.findByIdOrNull(characterId)
        val hissatsu = hissatsuRepo.findByIdOrNull(hissatsuId)

        val assignment = CharacterHissatsus(
            id = CharacterHissatsuId(characterId, hissatsuId),
            character = character,
            hissatsu = hissatsu
        )
        return repository.save(assignment)
    }

    @Transactional
    fun removeHissatsuFromCharacter(characterId: Int, hissatsuId: Int): Boolean {
        return repository.deleteByCharacterIdAndHissatsuId(characterId, hissatsuId) > 0
    }

    fun getHissatsusForCharacter(characterId: Int): List<Hissatsu> {
        return repository.findHissatsusByCharacterId(characterId)
    }

    fun getCharactersWithHissatsu(characterId: Int): List<Hissatsu> {
        return repository.findHissatsusByCharacterId(characterId)
    }
}