import kotlinx.serialization.Serializable

@Serializable
data class Projects(
    val id: Int,
    val project_name: String,
    val project_desc: String,
    val tech_stack: String
)
