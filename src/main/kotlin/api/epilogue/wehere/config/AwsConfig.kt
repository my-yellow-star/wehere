package api.epilogue.wehere.config

import api.epilogue.wehere.client.application.FileUploader
import api.epilogue.wehere.client.infra.S3FileUploader
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AwsProperties::class)
class AwsConfig(
    private val properties: AwsProperties
) {
    private val credential: BasicAWSCredentials
        get() = BasicAWSCredentials(
            properties.credentials.accessKey,
            properties.credentials.secretKey
        )

    @Bean
    fun s3Uploader(): FileUploader =
        S3FileUploader(amazonS3Client(), properties.s3.bucket)

    fun amazonS3Client(): AmazonS3Client =
        AmazonS3ClientBuilder
            .standard()
            .withRegion(properties.region.static)
            .withCredentials(AWSStaticCredentialsProvider(credential))
            .build() as AmazonS3Client
}

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws")
data class AwsProperties(
    val credentials: Credentials,
    val s3: S3,
    val region: Region
) {
    companion object {
        data class Credentials(
            val accessKey: String,
            val secretKey: String
        )

        data class S3(
            val bucket: String
        )

        data class Region(
            val static: String
        )
    }
}
