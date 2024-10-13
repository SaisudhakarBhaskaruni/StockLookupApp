import com.example.leelasai.stocklookupapp.StockApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://financeapi.net/" // Ensure there is no space after the URL

    // Setup logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Build OkHttp client
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Build Gson with lenient parsing
    private val gson = GsonBuilder()
        .setLenient() // This allows lenient parsing of JSON
        .create()

    // Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use lenient Gson
            .build()
    }

    // API service
    val stockApi: StockApiService by lazy {
        retrofit.create(StockApiService::class.java)
    }
}
