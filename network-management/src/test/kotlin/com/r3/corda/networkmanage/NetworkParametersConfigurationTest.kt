package com.r3.corda.networkmanage

import com.r3.corda.networkmanage.doorman.parseNetworkParametersFrom
import net.corda.core.utilities.days
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import java.io.File
import java.nio.file.Paths
import java.time.Instant

class NetworkParametersConfigurationTest {

    private val validOverrideNetworkConfigPath = File("network-parameters.conf").toPath()

    @Test
    fun `reads an existing file`() {
        val networkParameters = parseNetworkParametersFrom(validOverrideNetworkConfigPath)
        assertThat(networkParameters.minimumPlatformVersion).isEqualTo(1)
        val notaries = networkParameters.notaries
        assertThat(notaries).hasSize(2)
        assertThat(notaries[0].validating).isTrue()
        assertThat(notaries[1].validating).isFalse()
        assertThat(networkParameters.maxMessageSize).isEqualTo(100)
        assertThat(networkParameters.maxTransactionSize).isEqualTo(100)
        // This is rather weak, though making this an exact test will require mocking a clock.
        assertThat(networkParameters.modifiedTime).isBefore(Instant.now())
        assertThat(networkParameters.epoch).isEqualTo(1)
    }

    @Test
    fun `throws on a non-existing file`() {
        assertThatThrownBy {
            parseNetworkParametersFrom(Paths.get("notHere"))
        }.isInstanceOf(IllegalStateException::class.java)
    }
}