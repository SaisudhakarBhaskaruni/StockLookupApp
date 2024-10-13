import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leelasai.stocklookupapp.StockResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockViewModel : ViewModel() {
    private val _companyName = MutableLiveData<String>()
    val companyName: LiveData<String> get() = _companyName

    private val _stockPrice = MutableLiveData<String>()
    val stockPrice: LiveData<String> get() = _stockPrice

    private val _percentageChange = MutableLiveData<String>()
    val percentageChange: LiveData<String> get() = _percentageChange

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchStockData(symbol: String) {
        val call = RetrofitInstance.stockApi.getStockData(symbol)
        call.enqueue(object : Callback<StockResponse> {
            override fun onResponse(call: Call<StockResponse>, response: Response<StockResponse>) {
                if (response.isSuccessful) {
                    val stockData = response.body()
                    _companyName.value = stockData?.companyName
                    _stockPrice.value = stockData?.currentPrice.toString()
                    _percentageChange.value = stockData?.percentageChange.toString()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<StockResponse>, t: Throwable) {
                _errorMessage.value = "Failure: ${t.message}"
            }
        })
    }
}
