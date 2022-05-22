import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Tickets(
    @JsonNames("id_tickets") val id: Int,
    @JsonNames("ticket_number") val ticketNumber: String,
    @JsonNames("ticket_name") val ticketName: String,
    @JsonNames("ticket_desc") val ticketDesc: String?,
    @JsonNames("ticket_start") val ticketStart: String?,
    @JsonNames("ticket_due") val ticketDueDate: String?,
    @JsonNames("ticket_status") val ticketStatus: TicketStatus?
)
