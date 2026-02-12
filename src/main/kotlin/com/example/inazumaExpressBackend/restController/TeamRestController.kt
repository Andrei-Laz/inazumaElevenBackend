// restController/TeamController.kt
package com.example.inazumaExpressBackend.restController

import com.example.inazumaExpressBackend.model.Team
import com.example.inazumaExpressBackend.model.TeamMember
import com.example.inazumaExpressBackend.service.TeamService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/teams")
class TeamRestController(
    private val service: TeamService
) {

    // Get all teams
    @GetMapping
    fun getAllTeams(): ResponseEntity<List<Team>> {
        return ResponseEntity.ok(service.getAllTeams())
    }

    // Get team by ID
    @GetMapping("/{id}")
    fun getTeam(@PathVariable id: Int): ResponseEntity<Team> {
        return service.getTeamById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    // Create new team
    @PostMapping
    fun createTeam(@RequestParam name: String): ResponseEntity<Team> {
        return try {
            ResponseEntity.ok(service.createTeam(name))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    // Update team name
    @PutMapping("/{id}")
    fun updateTeam(@PathVariable id: Int, @RequestParam name: String): ResponseEntity<Team> {
        return service.updateTeam(id, name)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    // Delete team
    @DeleteMapping("/{id}")
    fun deleteTeam(@PathVariable id: Int): ResponseEntity<Void> {
        return if (service.deleteTeam(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Add character to team
    @PostMapping("/{teamId}/members")
    fun addCharacterToTeam(
        @PathVariable teamId: Int,
        @RequestParam characterId: Int
    ): ResponseEntity<TeamMember> {
        return try {
            ResponseEntity.ok(service.addCharacterToTeam(teamId, characterId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    // Remove character from team
    @DeleteMapping("/{teamId}/members/{characterId}")
    fun removeCharacterFromTeam(
        @PathVariable teamId: Int,
        @PathVariable characterId: Int
    ): ResponseEntity<Void> {
        return if (service.removeCharacterFromTeam(teamId, characterId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Get all members of a team
    @GetMapping("/{teamId}/members")
    fun getTeamMembers(@PathVariable teamId: Int): ResponseEntity<List<TeamMember>> {
        return ResponseEntity.ok(service.getTeamMembers(teamId))
    }
}