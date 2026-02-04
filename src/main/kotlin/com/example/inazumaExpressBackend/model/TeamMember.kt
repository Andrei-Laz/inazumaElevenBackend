package com.example.inazumaExpressBackend.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "team_members",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["team_id", "team_position"]), // One player per position
        UniqueConstraint(columnNames = ["team_id", "character_id"])  // No duplicate characters
    ]
)
data class TeamMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    var teamMemberId: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    var team: Team,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    var character: Character,

    @Column(name = "team_position", nullable = false)
    var teamPosition: Int = 1, // 1-16, determines display order in UI

    @Column(name = "is_captain")
    var isCaptain: Boolean = false,

    @Column(name = "is_substitute")
    var isSubstitute: Boolean = false
)