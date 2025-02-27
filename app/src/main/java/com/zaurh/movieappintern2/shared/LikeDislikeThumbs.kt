package com.zaurh.movieappintern2.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaurh.movieappintern2.R
import com.zaurh.movieappintern2.data.models.firebase.reviews.Rate
import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData

@Composable
fun LikeDislikeThumbs(
    modifier: Modifier,
    reviewData: ReviewData
) {
    Row(modifier = modifier
        .padding(8.dp)) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = R.drawable.dislike_ic),
            contentDescription = "",
            tint = if (reviewData.rate == Rate.DISLIKE) Color.Red.copy(
                alpha = 0.5f
            ) else MaterialTheme.colorScheme.primary.copy(0.2f)
        )
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = R.drawable.like_ic),
            contentDescription = "",
            tint = if (reviewData.rate == Rate.LIKE) Color.Green.copy(
                0.5f
            ) else MaterialTheme.colorScheme.primary.copy(0.2f)
        )
    }
}