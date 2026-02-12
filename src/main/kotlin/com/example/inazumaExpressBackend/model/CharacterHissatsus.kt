package com.example.inazumaExpressBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class CharacterHissatsuId(
    var characterId: Int = 0,
    var hissatsuId: Int = 0
) : Serializable

@Entity
@Table(
    name = "character_hissatsus",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["character_id", "hissatsu_id"])
    ]
)
data class CharacterHissatsus(
    @EmbeddedId
    var id: CharacterHissatsuId = CharacterHissatsuId(),

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("characterId")
    @JoinColumn(name = "character_id", nullable = false)
    var character: Character? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hissatsuId")
    @JoinColumn(name = "hissatsu_id", nullable = false)
    var hissatsu: Hissatsu? = null
)