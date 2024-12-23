package com.example.ridenow

import android.app.Fragment
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ridenow.ui.theme.RideNowTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ridenow.model.Motorcycle
import com.example.ridenow.viewmodel.MotorcycleViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.UUID


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideNowTheme {
                AppContent() // Display the main app content
            }
        }
    }
}
/*
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginRegisterScreen(navController)
        }
        composable("item_list") {
            ItemListScreen()
        }
    }
}*/

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var viewModel: MotorcycleViewModel = viewModel()
    var showFab by remember { mutableStateOf(false) }
    //LoginRegisterScreen(navController)
    Scaffold(
        floatingActionButton = {
            // Show FAB only on the "item_list" screen
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentRoute = currentBackStackEntry?.destination?.route
            if (currentRoute == "item_list") {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_motorcycle")
                    },
                    containerColor = Color(0xFFFF4B5C) // Neon pink or red color
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues) // Adjusts for padding due to the Scaffold
        ) {

            composable("item_list") {
                ItemListScreen(viewModel,navController)
            }
            composable("add_motorcycle") {
                AddMotorcycleScreen(navController,viewModel) // A screen for adding a new motorcycle
            }
            composable("login")
            {
                LoginRegisterScreen(navController)
            }
            composable("edit/{motorcycleId}") { backStackEntry ->
                val motorcycleId = backStackEntry.arguments?.getString("motorcycleId")?.toIntOrNull()
                if (motorcycleId != null) {
                    EditMotorcycleScreen(navController, viewModel, motorcycleId)
                }
            }
        }
    }
}


@Composable
fun AppContent() {
    var showSplash by remember { mutableStateOf(true) }
    //val navController = rememberNavController()
    if (showSplash) {
        SplashScreen(onSplashFinished = { showSplash = false })
    } else {
        AppNavigation();
    }
}

@Composable
fun LoginRegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.image1), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark overlay to enhance text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        // App Name Text
        Text(
            text = "RideNow",
            fontSize = 40.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .shadow(8.dp) // Shadow effect for more contrast
        )

        // Buttons Column
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("item_list") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B5C)), // Neon pink or red
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .shadow(10.dp)
            ) {
                Text(text = "Login", fontSize = 18.sp, color = Color.White)
            }

            Button(
                onClick = { /* Navigate to Register */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B5C)), // Neon pink or red
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .shadow(10.dp)
            ) {
                Text(text = "Register", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    // Start fading out after a delay
    LaunchedEffect(Unit) {
        delay(2000) // Wait for 2 seconds
        isVisible = false
        delay(500) // Fade duration
        onSplashFinished() // Call to navigate to the next screen
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    // Black background with text
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "RideNow",
            fontSize = 36.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to RideNow!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RideNowTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    RideNowTheme {
        WelcomeScreen()
    }
}

@Composable
fun ItemListScreen(viewModel: MotorcycleViewModel,navController: NavController) {
    val items = viewModel.items.observeAsState(initial = mutableListOf()).value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items.size) { index ->
                val motorcycle = items[index]
                MotorcycleItem(
                    motorcycle = motorcycle,
                    onEditClick = {
                        navController.navigate("edit/${motorcycle.id}")
                    },
                    onDeleteClick = {
                        viewModel.deleteMotorcycle(motorcycle.id) // Call the delete function
                    }
                )
            }
        }
    }
}

/*@Composable
fun MotorcycleItem(motorcycle: Motorcycle, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    // Replace this with your Motorcycle item layout
    Text(
        text = "${motorcycle.make} ${motorcycle.model} (${motorcycle.year}) - ${motorcycle.power} HP",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onEditClick)
    )
}*/

@Composable
fun MotorcycleItem(
    motorcycle: Motorcycle,
    onEditClick: (Motorcycle) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Motorcycle Name
        Text(
            text = "${motorcycle.make} ${motorcycle.model} (${motorcycle.year})",
            color = Color(0xFFFFA500), // holo_orange_light
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )

        // Edit Button
        Text(
            text = "EDIT",
            color = Color(0xFF00FF00), // holo_green_light
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onEditClick(motorcycle) }
        )

        // Delete Button
        Text(
            text = "DEL",
            color = Color(0xFFFF0000), // holo_red_light
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDeleteClick(motorcycle.id) }
        )
    }
}

@Composable
fun AddMotorcycleScreen(navController: NavController,viewModel: MotorcycleViewModel) {
    // State variables for form inputs
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var power by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Motorcycle",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Make TextField
        OutlinedTextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Model TextField
        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Year TextField
        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        // Power TextField
        OutlinedTextField(
            value = power,
            onValueChange = { power = it },
            label = { Text("Power (HP)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // Implement save functionality here
                    //if (make.isNotBlank() && model.isNotBlank() && year.isNotBlank() && power.isNotBlank()) {
                        val motorcycle = Motorcycle(
                            id = (viewModel.items.value?.size ?: 0) + 1, // Generate a sequential integer ID
                            make = make,
                            model = model,
                            year = year.toIntOrNull() ?: 0, // Default to 0 if parsing fails
                            power = power.toIntOrNull() ?: 0 // Default to 0 if parsing fails
                        )

                    //val bike = Motorcycle(1, "Yamaha", "MT-07", 2014, 75)


                    viewModel.addMotorcycle(motorcycle)
                    // Save the motorcycle data and go back to the list
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green save button
            ) {
                Text("Save")
            }

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B5C)) // Red cancel button
            ) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun EditMotorcycleScreen(navController: NavController,viewModel: MotorcycleViewModel,motorcycleId: Int) {
    // Retrieve the motorcycle from the ViewModel using the ID
    val motorcycle = viewModel.getMotorcycleById(motorcycleId)
    var make by remember { mutableStateOf(motorcycle?.make ?: "") }
    var model by remember { mutableStateOf(motorcycle?.model ?: "") }
    var year by remember { mutableStateOf(motorcycle?.year?.toString() ?: "") }
    var power by remember { mutableStateOf(motorcycle?.power?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Edit Motorcycle Info",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Make TextField
        OutlinedTextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Model TextField
        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Year TextField
        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Power TextField
        OutlinedTextField(
            value = power,
            onValueChange = { power = it },
            label = { Text("Power (HP)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    val updatedMotorcycle = Motorcycle(
                        id = motorcycleId,
                        make = make,
                        model = model,
                        year = year.toIntOrNull() ?: 0,
                        power = power.toIntOrNull() ?: 0
                    )
                    viewModel.updateMotorcycle(updatedMotorcycle)
                    navController.popBackStack() // Go back to the item list screen
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green save button
            ) {
                Text("Save")
            }

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B5C)) // Red cancel button
            ) {
                Text("Cancel")
            }
        }
    }
}



