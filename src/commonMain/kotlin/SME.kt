import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SME(
    @JsonNames("id_sme") val id: Int,
    @JsonNames("sme_name") val name: String
)
