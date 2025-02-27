package com.zaurh.movieappintern2.presentation.profile.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData
import com.zaurh.movieappintern2.domain.repository.ReviewRepository
import com.zaurh.movieappintern2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MyReviewsState(
    val reviews: List<ReviewData> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = ""
)
@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _myReviewsState = MutableStateFlow(MyReviewsState())
        val myReviewsState = _myReviewsState.asStateFlow()


    fun getMyReviews(userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            reviewRepository.getMyReviews(userId).collect{ result ->
                when(result){
                    is Resource.Error -> {
                        _myReviewsState.value = MyReviewsState(message = result.message ?: "")
                    }
                    is Resource.Loading -> {
                        _myReviewsState.value = MyReviewsState(isLoading = true)
                    }
                    is Resource.Success -> {
                        val reviewList = result.data ?: emptyList()
                        if (reviewList.isEmpty()){
                            _myReviewsState.value = MyReviewsState(message = "No reviews found.")
                            return@collect
                        }
                        _myReviewsState.value = MyReviewsState(reviews = reviewList)
                    }
                }
            }
        }
    }

}