package com.example.inazumaExpressBackend.model

import com.example.inazumaExpressBackend.model.enums.Element
import com.example.inazumaExpressBackend.model.enums.Position
import com.example.inazumaExpressBackend.model.enums.Sex
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Table(name = "characters")
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    var characterId: Int? = null,

    @Column(nullable = false)
    var nickname: String = "",

    @Column
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column
    var sex: Sex = Sex.UNKNOWN,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var element: Element = Element.VOID,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var position: Position = Position.ND,

    // Stats
    @Column
    var kick: Int = 0,
    @Column
    var dribble: Int = 0,
    @Column
    var block: Int = 0,
    @Column
    var catch: Int = 0,
    @Column
    var technique: Int = 0,
    @Column
    var speed: Int = 0,
    @Column
    var stamina: Int = 0,
    @Column
    var luck: Int = 0,

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true)
    val teamMemberships: MutableSet<TeamMember> = mutableSetOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = [CascadeType.ALL], orphanRemoval = true)
    val hissatsuAssignments: MutableSet<CharacterHissatsus> = mutableSetOf()
)