package hello

import javax.servlet.ServletContext

import org.springframework.boot.SpringApplication
import org.springframework.boot.context.embedded.{FilterRegistrationBean, ServletRegistrationBean}
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.ShallowEtagHeaderFilter

/**
 * This object bootstraps Spring Boot web application.
 * Via Gradle: gradle bootRun
 *
 * @author saung
 * @since 1.0
 */
object HelloWebApplication {

	def main(args: Array[String]) {
	   SpringApplication.run(classOf[HelloConfig]);


	}
}
