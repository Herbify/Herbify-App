import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herbify.herbifyapp.data.remote.response.article.AddNewArticleResponse
import com.herbify.herbifyapp.repository.ArticleRepository
import com.herbify.herbifyapp.utils.RepositoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AddNewPostViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

//    private val _addNewPostResult = MutableLiveData<RepositoryResult<AddNewArticleResponse>?>()
//    val addNewPostResult: LiveData<RepositoryResult<AddNewArticleResponse>?> = _addNewPostResult
//
//    fun addNewArticle(title: String, photo: File, content: String, tag1: String, tag2: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val result = articleRepository.addNewArticle(title, photo, content, tag1, tag2)
//                _addNewPostResult.postValue(result)
//            } catch (e: Exception) {
//                _addNewPostResult.postValue(null)
//            }
//        }
//    }
}
