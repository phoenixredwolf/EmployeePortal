import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Consultants(
    @JsonNames("id_consultant") val idConsultant: Int,
    val name: String,
    val current_project: Projects?,
    val previous_projects: List<Projects>?,
    @JsonNames("rave_start") val raveStart: String?,
    val project_start: String?,
    val sme: SME?
)
