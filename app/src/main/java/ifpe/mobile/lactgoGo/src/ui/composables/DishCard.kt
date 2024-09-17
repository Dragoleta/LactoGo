package ifpe.mobile.lactgoGo.src.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ifpe.mobile.lactgoGo.R
import ifpe.mobile.lactgoGo.src.database.models.DishModel

@Composable
fun DishIndoCard(
    dish: DishModel,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp,end = 5.dp, start = 5.dp)
            .fillMaxWidth()
            .height(125.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 0.dp, start = 1.dp)
                    .align(Alignment.Start)
            ) {
                Column(modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp)) {
                    Text(
                        text = dish.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    VerticalDivider(modifier = Modifier.height(7.dp), color = Color.Transparent)
                    Text(
                        text = dish.description,
                        fontSize = 16.sp,
                        color = Color.White
                    )

                    VerticalDivider(modifier = Modifier.height(10.dp),color = Color.Transparent)
                    Row(modifier.width(175.dp)) {
                        for (ingredient in dish.ingredients) {
                            Text(ingredient)
                            HorizontalDivider(modifier.width(10.dp))}

                    }
                }

                HorizontalDivider(modifier = Modifier.width(20.dp),color = Color.Transparent)
                Image(
                    painter = painterResource(id = R.drawable.sr2adb75e98031c),
                    contentDescription = stringResource(id = R.string.project_id),
                    modifier = modifier.width(90.dp).height(90.dp)
                )
            }

    }

}