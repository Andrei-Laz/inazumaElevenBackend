package com.example.inazumaExpressBackend.service

import com.example.inazumaExpressBackend.model.Team
import com.example.inazumaExpressBackend.model.TeamMember
import com.example.inazumaExpressBackend.repository.CharacterRepository
import com.example.inazumaExpressBackend.repository.TeamMemberRepository
import com.example.inazumaExpressBackend.repository.TeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService(
    private val teamRepo: TeamRepository,
    private val memberRepo: TeamMemberRepository,
    private val characterRepo: CharacterRepository
) {

    // Team CRUD
    fun getAllTeams(): List<Team> = teamRepo.findAll()

    fun getTeamById(id: Int): Team? = teamRepo.findById(id).orElse(null)

    fun createTeam(name: String): Team {
        if (teamRepo.existsByTeamName(name))
            throw RuntimeException("Team name already exists")
        return teamRepo.save(Team(teamName = name))
    }

    fun updateTeam(id: Int, newName: String): Team? {
        val team = teamRepo.findById(id).orElse(null) ?: return null
        team.teamName = newName
        return teamRepo.save(team)
    }

    @Transactional
    fun deleteTeam(id: Int): Boolean {
        return if (teamRepo.existsById(id)) {
            teamRepo.deleteById(id)
            true
        } else false
    }

    @Transactional
    fun addCharacterToTeam(teamId: Int, characterId: Int): TeamMember {
        if (!teamRepo.existsById(teamId))
            throw RuntimeException("Team not found")

        val team = teamRepo.findById(teamId).orElseThrow { RuntimeException("Team not found") }
        val character = characterRepo.findById(characterId)
            .orElseThrow { RuntimeException("Character not found") }

        if (memberRepo.existsByTeamTeamIdAndCharacterCharacterId(teamId, characterId))
            throw RuntimeException("Character already in team")

        val member = TeamMember(
            team = team,
            character = character
        )
        return memberRepo.save(member)
    }

    @Transactional
    fun removeCharacterFromTeam(teamId: Int, characterId: Int): Boolean {
        return memberRepo.deleteByTeamTeamIdAndCharacterCharacterId(teamId, characterId) > 0
    }

    fun getTeamMembers(teamId: Int): List<TeamMember> {
        return memberRepo.findByTeamTeamId(teamId)
    }

    fun getCharactersInTeam(characterId: Int): List<TeamMember> {
        return memberRepo.findByCharacterCharacterId(characterId)
    }
}