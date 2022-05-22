enum class TicketStatus(val status: Int) {
    NOT_STARTED(0),
    IN_PROGRESS(1),
    IN_PR(2),
    IN_QA(3),
    COMPLETED(4),
    BLOCKED(5),
    WITHDRAWN(6)
}