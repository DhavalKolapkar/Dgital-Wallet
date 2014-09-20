package hello

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author saung
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
class DigitalWalletConfig {
}