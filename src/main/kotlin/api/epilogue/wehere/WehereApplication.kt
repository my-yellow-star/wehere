package api.epilogue.wehere

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class WehereApplication

fun main(args: Array<String>) {
	runApplication<WehereApplication>(*args)
}
