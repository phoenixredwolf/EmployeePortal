import kotlinx.serialization.Serializable

@Serializable
data class ConsultantListItem(
    val consultant: Consultants,
    val tickets: List<Tickets>?
){
    companion object {
        const val path = "/consultants"
    }
}
