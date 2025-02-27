package com.zaurh.movieappintern2.presentation.profile.reviews.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zaurh.movieappintern2.R
import com.zaurh.movieappintern2.data.models.firebase.reviews.Rate
import com.zaurh.movieappintern2.data.models.firebase.reviews.ReviewData
import com.zaurh.movieappintern2.shared.LikeDislikeThumbs
import com.zaurh.movieappintern2.util.formatTimestamp

@Composable
fun ReviewsItem(
    reviewData: ReviewData,
    onClick: (Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                reviewData.movie?.id?.let {
                    onClick(it)
                }
            }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(8f)) {
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = reviewData.movie?.poster,
                contentDescription = "",
                contentScale = ContentScale.Inside
            )
            Spacer(Modifier.size(8.dp))
            Column {
                Text(
                    text = "${reviewData.movie?.title}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                var opinionIsExpanded by remember { mutableStateOf(false) }
                Text(
                    text = reviewData.opinion.ifEmpty { "No review" },
                    color = if (reviewData.opinion.isEmpty()) MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.5f
                    ) else MaterialTheme.colorScheme.primary,
                    maxLines = if (opinionIsExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable {
                        opinionIsExpanded = !opinionIsExpanded
                    }
                )
                Text(
                    text = formatTimestamp(reviewData.timestamp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )

            }
        }

        LikeDislikeThumbs(
            modifier = Modifier.weight(2f),
            reviewData = reviewData
        )

    }
}