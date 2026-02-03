package com.example.inazumaExpressBackend.repository

import com.example.inazumaExpressBackend.model.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository : JpaRepository<Character, Int> {
    fun findByNickname(nickname: String): List<Character>
}