import kotlinx.serialization.Serializable

@Serializable
data class Consultants(
    val id: Int,
    val name: String,
    val current_project: Projects?,
    val previous_projects: List<Projects>?,
    val rave_start: String?,
    val project_start: String?,
    val sme: SME?
)
