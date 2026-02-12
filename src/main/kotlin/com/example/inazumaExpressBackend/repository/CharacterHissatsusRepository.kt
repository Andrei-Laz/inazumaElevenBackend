// repository/CharacterHissatsusRepository.kt
package com.example.inazumaExpressBackend.repository

import com.example.inazumaExpressBackend.model.CharacterHissatsuId
import com.example.inazumaExpressBackend.model.CharacterHissatsus
import com.example.inazumaExpressBackend.model.Hissatsu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CharacterHissatsusRepository : JpaRepository<CharacterHissatsus, CharacterHissatsuId> {

    @Query("SELECT ch FROM CharacterHissatsus ch WHERE ch.id.characterId = :characterId")
    fun findByCharacterId(characterId: Int): List<CharacterHissatsus>

    @Query("SELECT ch FROM CharacterHissatsus ch WHERE ch.id.hissatsuId = :hissatsuId")
    fun findByHissatsuId(hissatsuId: Int): List<CharacterHissatsus>

    @Query("SELECT CASE WHEN COUNT(ch) > 0 THEN true ELSE false END FROM CharacterHissatsus ch WHERE ch.id.characterId = :characterId AND ch.id.hissatsuId = :hissatsuId")
    fun existsByCharacterIdAndHissatsuId(characterId: Int, hissatsuId: Int): Boolean

    @Modifying
    @Query("DELETE FROM CharacterHissatsus ch WHERE ch.id.characterId = :characterId AND ch.id.hissatsuId = :hissatsuId")
    fun deleteByCharacterIdAndHissatsuId(characterId: Int, hissatsuId: Int): Int

    @Query("""
        SELECT h FROM Hissatsu h 
        JOIN CharacterHissatsus ch ON h.hissatsuId = ch.id.hissatsuId 
        WHERE ch.id.characterId = :characterId
    """)
    fun findHissatsusByCharacterId(characterId: Int): List<Hissatsu>
}