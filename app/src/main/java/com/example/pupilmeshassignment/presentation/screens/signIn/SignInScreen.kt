package com.example.pupilmeshassignment.presentation.screens.signIn

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pupilmeshassignment.presentation.components.AuthComponent
import com.example.pupilmeshassignment.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel()
) {

    val context = LocalContext.current

    val state = viewModel.signInState
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onErrorConsumed()
        }
    }

    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            Toast.makeText(context, "Signed In Successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Manga.route) {
                popUpTo(Screen.SignIn.route) { inclusive = true }
            }
        }
    }


    Box(
        modifier = modifier.fillMaxSize().padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "close")
            Text("Sign In", style = MaterialTheme.typography.bodyMedium)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AuthComponent(modifier = Modifier, text = "Welcome Back", use = "sign in")

                    // Email input
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = { Text(text = "Your Email Address") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    // Password input
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )

                    // Forgot Password
                    Text(
                        text = "Forgot Password?",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { /* handle forgot password */ },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    )

                    // Sign In Button
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onSignInClick() },
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(text = "Sign In")
                        }
                    }

                    // Sign Up text

                    // Sign Up text
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {
                                // Handle click here later
                            }
                        )
                    }
//                    ClickableText(
//                        text = buildAnnotatedString {
//                            append("Don't have an account? ")
//                            withStyle(
//                                style = SpanStyle(
//                                    color = MaterialTheme.colorScheme.primary,
//                                    textDecoration = TextDecoration.Underline
//                                )
//                            ) {
//                                append("Sign Up")
//                            }
//                        },
//                        onClick = { offset ->
//                            // You can detect click range if needed
//                            // For now just assume they clicked Sign Up
//                        }
//                    )
                }

                // Error message
                state.errorMessage?.let { error ->
                    LaunchedEffect(error) {
                        // Show error, snackbar or whatever
                        viewModel.onErrorConsumed()
                    }
                }
            }
        }
    }
}


