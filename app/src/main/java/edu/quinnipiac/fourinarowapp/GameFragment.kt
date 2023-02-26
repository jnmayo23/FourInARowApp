/**
 * Game Fragment -- GameFragment Class
 * @author Jordan Mayo
 * @date 2/25/2023
 */

package edu.quinnipiac.fourinarowapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class GameFragment : Fragment() {

    val FIR_board = FourInARow()
    var currentState: Int = GameConstants.PLAYING
    var winner = 0
    var isColumnFull = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val usernameText = view.findViewById<TextView>(R.id.username_move)
        usernameText.text = arguments?.getString("username") + "'s move"

        // Resets board and game
        val resetButton = view.findViewById<Button>(R.id.reset)
        resetButton.setOnClickListener() {
            FIR_board.clearBoard()
            updateBoard()

            usernameText.visibility = VISIBLE
            val moveText = view?.findViewById<EditText>(R.id.move_input)
            moveText?.visibility = VISIBLE
            val playMoveButton = view.findViewById<Button>(R.id.play_move)
            playMoveButton.visibility = VISIBLE
            val gameResult = view?.findViewById<TextView>(R.id.game_result)
            gameResult?.visibility = INVISIBLE
            val resetButton = view.findViewById<Button>(R.id.reset)
            resetButton.visibility = INVISIBLE
        }

        // Sets player move, followed by generated computer move, if it is valid
        val playMoveButton = view.findViewById<Button>(R.id.play_move)
        playMoveButton.setOnClickListener() {
            val moveText = view.findViewById<EditText>(R.id.move_input)
            val move = moveText.text.toString()
            if (move.toInt() < 0 || move.toInt() > 5) {
                moveText.getText().clear()
            } else {
                FIR_board.setMove(GameConstants.RED, move.toInt())
                moveText.getText().clear()
                if (FIR_board.isColumnFull == true) {
                    val computerText = view.findViewById<TextView>(R.id.computer_move)
                    computerText.text = "Invalid entry, try again."
                    moveText.getText().clear()
                } else {
                    updateBoard()
                    usernameText.visibility = INVISIBLE

                    var comp_move: Int = FIR_board.computerMove
                    do {
                        comp_move = FIR_board.computerMove
                        FIR_board.setMove(GameConstants.BLUE, comp_move)
                    } while (FIR_board.isColumnFull == true)
                    val computerText = view.findViewById<TextView>(R.id.computer_move)
                    computerText.text = "Computer played column " + comp_move
                    computerText.visibility = VISIBLE
                    usernameText.visibility = VISIBLE

                    updateBoard()
                }
            }
        }
        return view
    }


    fun updateBoard() {
        // Matches visual board (TableLayout) with the 2D array to update screen
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                val position = (row * 6) + col
                val tempName = "pos" + position.toString()
                val space = resources.getIdentifier(tempName, "id", requireContext().packageName)

                if (FIR_board.board[row][col] == GameConstants.EMPTY) {
                    view?.findViewById<ImageView>(space)?.setImageDrawable(getResources().getDrawable(R.drawable.empty))
                } else if (FIR_board.board[row][col] == GameConstants.RED) {
                    view?.findViewById<ImageView>(space)?.setImageDrawable(getResources().getDrawable(R.drawable.red))
                } else if (FIR_board.board[row][col] == GameConstants.BLUE) {
                    view?.findViewById<ImageView>(space)?.setImageDrawable(getResources().getDrawable(R.drawable.blue))
                }
            }
        }

        // Check for winner
        winner = FIR_board.checkForWinner()
        if (winner != 0) {
            if (winner == GameConstants.RED) {
                currentState = GameConstants.RED_WON
            } else if (winner == GameConstants.BLUE) {
                currentState = GameConstants.BLUE_WON
            }
            gameOver()
        } else {
            var isTie = FIR_board.checkForTie()
            if (isTie == GameConstants.TIE) {
                currentState = GameConstants.TIE
                gameOver()
            }
        }
    }

    // Displays game result message
    fun gameOver() {
        val usernameText = view?.findViewById<TextView>(R.id.username_move)
        usernameText?.visibility = INVISIBLE
        val computerText = view?.findViewById<TextView>(R.id.computer_move)
        computerText?.visibility = INVISIBLE
        val moveText = view?.findViewById<EditText>(R.id.move_input)
        moveText?.visibility = INVISIBLE
        val playMoveButton = view?.findViewById<Button>(R.id.play_move)
        playMoveButton?.visibility = INVISIBLE

        val gameResult = view?.findViewById<TextView>(R.id.game_result)
        if (currentState == GameConstants.RED_WON) {
            gameResult?.text = "Game Over, You Win!"
        } else if (currentState == GameConstants.BLUE_WON) {
            gameResult?.text = "Game Over, You Lose!"
        } else if (currentState == GameConstants.TIE) {
            gameResult?.text = "Tie Game!"
        }
        gameResult?.visibility = VISIBLE
        val resetButton = view?.findViewById<Button>(R.id.reset)
        resetButton?.visibility = VISIBLE
    }
}