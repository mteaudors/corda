package net.corda.core.internal

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.DeleteForDJVM
import net.corda.core.node.ServiceHub
import net.corda.core.node.StatesToRecord
import java.util.concurrent.ExecutorService

// TODO: This should really be called ServiceHubInternal but that name is already taken by net.corda.node.services.api.ServiceHubInternal.
@DeleteForDJVM
interface ServiceHubCoreInternal : ServiceHub {

    val externalOperationExecutor: ExecutorService

    val attachmentTrustCalculator: AttachmentTrustCalculator

    fun createTransactionsResolver(flow: ResolveTransactionsFlow): TransactionsResolver
}

interface TransactionsResolver {
    @Suspendable
    fun downloadDependencies(batchMode: Boolean)

    fun recordDependencies(usedStatesToRecord: StatesToRecord)
}