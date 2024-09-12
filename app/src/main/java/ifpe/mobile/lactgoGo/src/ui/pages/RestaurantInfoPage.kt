package ifpe.mobile.lactgoGo.src.ui.pages

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel

@Composable
fun RestaurantInfoPage(
    context: Context, viewModel : MainViewModel, modifier :Modifier,
    ) {



    println("Banana ${viewModel.restaurant}")

    Column(modifier.fillMaxHeight().fillMaxWidth()) {

        Card(
            modifier
                .background(color = Color.White)
                .widthIn(min = 350.dp)
                .heightIn(min = 100.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Row {
                Box(modifier.background(color = Color.Red).widthIn(min = 50.dp)) {}
                Column {
                    Text("ok")
                    Text("ok")
                    Text("ok")
                }
            }

        }

        Column (modifier.fillMaxHeight().background(color = Color.Yellow)){
            Card {  }
        }
    }

}