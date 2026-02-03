package com.example.inazumaExpressBackend.model

import com.example.inazumaExpressBackend.model.enums.Element
import com.example.inazumaExpressBackend.model.enums.Position
import com.example.inazumaExpressBackend.model.enums.Sex
import jakarta.persistence.*

@Entity
@Table(name = "characters")
data class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var characterId: Int? = null,

    @Column(nullable = false)
    var nickname: String = "",

    @Column
    var name: String = "",

    @Enumerated(EnumType.STRING)
    @Column
    var sex: Sex,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var element: Element,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var position: Position,

    //Stats
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
    var luck: Int = 0
)