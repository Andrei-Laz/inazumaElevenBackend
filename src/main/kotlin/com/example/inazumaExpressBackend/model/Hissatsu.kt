package com.example.inazumaExpressBackend.model

import com.example.inazumaExpressBackend.model.enums.Element
import jakarta.persistence.*

@Entity
@Table(name = "hissatsus")
data class Hissatsu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hissatsu_id")
    var hissatsuId: Int? = null,

    @Column(nullable = false)
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var element: Element = Element.VOID,

    @Column
    var description: String = "",

    @Column(name = "hissatsu_type")
    var type: String = "", // "shoot", "block", "dribble", "catch", etc.

    @Column
    var power: Int = 0,

    @OneToMany(mappedBy = "hissatsu", cascade = [CascadeType.ALL], orphanRemoval = true)
    val characterAssignments: MutableSet<CharacterHissatsus> = mutableSetOf()
)