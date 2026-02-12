// repository/TeamMemberRepository.kt
package com.example.inazumaExpressBackend.repository

import com.example.inazumaExpressBackend.model.TeamMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TeamMemberRepository : JpaRepository<TeamMember, Int> {

    @Query("SELECT tm FROM TeamMember tm WHERE tm.team.teamId = :teamId")
    fun findByTeamTeamId(teamId: Int): List<TeamMember>

    @Query("SELECT tm FROM TeamMember tm WHERE tm.character.characterId = :characterId")
    fun findByCharacterCharacterId(characterId: Int): List<TeamMember>

    @Query("SELECT CASE WHEN COUNT(tm) > 0 THEN true ELSE false END FROM TeamMember tm WHERE tm.team.teamId = :teamId AND tm.character.characterId = :characterId")
    fun existsByTeamTeamIdAndCharacterCharacterId(teamId: Int, characterId: Int): Boolean

    @Query("DELETE FROM TeamMember tm WHERE tm.team.teamId = :teamId AND tm.character.characterId = :characterId")
    fun deleteByTeamTeamIdAndCharacterCharacterId(teamId: Int, characterId: Int): Int
}