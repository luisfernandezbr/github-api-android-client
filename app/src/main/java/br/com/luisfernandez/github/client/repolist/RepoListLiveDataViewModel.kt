package br.com.luisfernandez.github.client.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.luisfernandez.github.client.http.CallbackWrapper
import br.com.luisfernandez.github.client.http.livedata.Resource
import br.com.luisfernandez.github.client.http.model.GitHubErrorBody
import br.com.luisfernandez.github.client.http.model.ServerError
import br.com.luisfernandez.github.client.pojo.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListLiveDataViewModel(
        private val repoListLiveDataModel: RepoListLiveDataModel
) : ViewModel() {

    private val mutableLiveDataLoad = MutableLiveData<SearchData>()

    val liveDataRepoList: LiveData<Resource<List<Repo>>> =
            Transformations.switchMap(mutableLiveDataLoad ) { searchData ->
                repoListLiveDataModel.loadRepoList(searchData.page, searchData.language)
            }

    fun doSearch(page: Int, language: String) {
        mutableLiveDataLoad.value = SearchData(page, language)
    }

    data class SearchData(
        val page: Int,
        val language: String
    )
}

