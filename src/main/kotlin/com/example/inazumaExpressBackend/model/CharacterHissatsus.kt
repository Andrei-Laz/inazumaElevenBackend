package com.example.inazumaExpressBackend.model

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class CharacterHissatsuId(
    var userId: Int = 0,
    var characterId: Int = 0
) : Serializable

@Entity
@Table(
    name = "character_hissatsu",
    uniqueConstraints = [
        // Optional: Prevent same hissatsu twice on same character
        UniqueConstraint(columnNames = ["user_id", "character_id", "hissatsu_id"])
    ]
)
data class CharacterHissatsus(
    @EmbeddedId
    var id: CharacterHissatsuId = CharacterHissatsuId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("characterId")
    @JoinColumn(name = "character_id", nullable = false)
    var character: Character? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hissatsu_id", nullable = false)
    var hissatsu: Hissatsu? = null
)