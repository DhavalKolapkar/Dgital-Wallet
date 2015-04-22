package hello

import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, Configuration, ComponentScan}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.filter.ShallowEtagHeaderFilter

/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author saung
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
class HelloConfig {
  @Bean
  def filterRegistrationBean(): FilterRegistrationBean = {
    val shallowETagFilter = new ShallowEtagHeaderFilter()
    val registrationBean = new FilterRegistrationBean()
    registrationBean.setFilter(shallowETagFilter)
    registrationBean.setOrder(1)

    registrationBean
  }
}