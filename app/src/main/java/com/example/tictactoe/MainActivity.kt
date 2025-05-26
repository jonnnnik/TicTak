
package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGame()
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame() {
    var board by remember { mutableStateOf(List(3) { MutableList(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    fun checkWinner(): String? {
        for (i in 0..2) {
            if (board[i][0] != "" && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0]
            if (board[0][i] != "" && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i]
        }
        if (board[0][0] != "" && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0]
        if (board[0][2] != "" && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2]
        return null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (winner != null) {
            Text("Победитель: $winner", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                board = List(3) { MutableList(3) { "" } }
                winner = null
                currentPlayer = "X"
            }) {
                Text("Сыграть ещё раз")
            }
        } else {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .clickable {
                                    if (board[i][j] == "" && winner == null) {
                                        board[i][j] = currentPlayer
                                        winner = checkWinner()
                                        if (winner == null) {
                                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(board[i][j], fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
