package pl.altkom.coffee.accounting.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.api.MemberCreatedEvent
import java.math.BigDecimal

@Aggregate
class Account {

    @AggregateIdentifier
    lateinit var memberId: String
    lateinit var balance: BigDecimal

    constructor()

    @CommandHandler
    fun on(command: OpenAccountCommand) {
        with(command) {
            AggregateLifecycle.apply(AccountOpenedEvent(memberId, balance))
        }
    }

    @EventSourcingHandler
    fun handle(event: MemberCreatedEvent) {
        memberId = event.memberId
    }

    @EventSourcingHandler
    fun handle(event: AccountOpenedEvent) {
        balance = event.balance
        memberId = event.memberId
    }
}
