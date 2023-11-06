import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FirebaseConfig(
    @Value("\${myapp.firebase-bucket}") private val bucketName: String,
) {
    @Bean
    fun initializeFirebaseApp(): FirebaseApp {
        val serviceAccount = ClassPathResource("serviceAccountKey.json").inputStream
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket(bucketName)
            .build()
        return FirebaseApp.initializeApp(options)
    }
}