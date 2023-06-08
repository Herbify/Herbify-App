import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.repository.ArticleRepository
import retrofit2.Call

class AddNewPostViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    fun addNewArticle(article: JsonObject): Call<AddNewArticleResponse> {
        return articleRepository.addNewArticle(article)
    }
}
