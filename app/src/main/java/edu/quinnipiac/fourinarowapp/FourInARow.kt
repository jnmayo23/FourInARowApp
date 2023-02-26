/**
 * Four In A Row Console Code -- FourInARow Class
 * @author Jordan Mayo
 * @date 2/2/2023
 */

package edu.quinnipiac.fourinarowapp

import kotlin.random.Random

class FourInARow: IGame {
    // Game board in 2D array initialized to zeros
    val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS){0} }
    var isColumnFull = false
    private var rowBottom = 5;

    // Clears board
    override fun clearBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                board[row][col] = GameConstants.EMPTY
            }
        }
    }

    // Sets player's move if it is valid
    override fun setMove(player: Int, location: Int) {
        for (i in 5 downTo 0) {
            // Calls setMove again to redo turn
            if (i == 0 && board[i][location] != GameConstants.EMPTY && player == GameConstants.RED) {
                println("Column is full, enter another")
                isColumnFull = true
                //val userInput = readLine()!!
                //setMove(GameConstants.RED, userInput.toInt())
                break
            } else if (i == 0 && board[i][location] != GameConstants.EMPTY && player == GameConstants.BLUE) {
                isColumnFull = true
                val compMove = computerMove
                //setMove(GameConstants.BLUE, compMove)
                break
            }
            if (board[i][location] == GameConstants.EMPTY) {
                rowBottom = i
                if (player == GameConstants.RED) {
                    board[rowBottom][location] = GameConstants.RED
                } else if (player == GameConstants.BLUE) {
                    board[rowBottom][location] = GameConstants.BLUE
                    println("Computer played column $location")
                }
                isColumnFull = false
                break
            }
        }
        return


    }

    // Generates computer move
    override val computerMove: Int
        get() = Random.nextInt(0, 6) // Plays random location

    // Checks board for any 4 in a row
    override fun checkForWinner(): Int {
        var winner = 0
        var redCount = 0
        var blueCount = 0

        // Checks Horizontally
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                if (board[row][col] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row][col] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row][col] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
            }
            if (winner != 0) {
                break
            } else {
                redCount = 0
                blueCount = 0
            }
        }

        // Checks Vertically
        for (col in 0 until GameConstants.COLS) {
            for (row in 0 until GameConstants.ROWS) {
                if (board[row][col] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row][col] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row][col] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
            }
            if (winner != 0) {
                break
            } else {
                redCount = 0
                blueCount = 0
            }
        }

        // Checks Diagonally
        var shift = 0
        var cutOff = 0
        for (row in 0 until GameConstants.ROWS / 2) {
            for (col in 0 until GameConstants.COLS - cutOff) {
                if (board[row + shift][col] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row + shift][col] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row + shift][col] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
                shift += 1
            }
            if (winner != 0) {
                break
            } else {
                shift = 0
                cutOff += 1
                redCount = 0
                blueCount = 0
            }
        }

        shift = 0
        cutOff = 2
        for (row in 3 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS - cutOff) {
                if (board[row - shift][col] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row - shift][col] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row - shift][col] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
                shift += 1
            }
            if (winner != 0) {
                break
            } else {
                shift = 0
                cutOff -= 1
                redCount = 0
                blueCount = 0
            }
        }

        shift = 0
        cutOff = 1
        for (col in 1 until GameConstants.COLS / 2) {
            for (row in 0 until GameConstants.COLS - cutOff) {
                if (board[row][col + shift] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row][col + shift] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row][col + shift] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
                shift += 1
            }
            if (winner != 0) {
                break
            } else {
                shift = 0
                cutOff += 1
                redCount = 0
                blueCount = 0
            }
        }

        shift = 0
        cutOff = 1
        for (col in 1 until GameConstants.COLS / 2) {
            for (row in 5 downTo 0 + cutOff) {
                if (board[row][col + shift] == GameConstants.RED && redCount != 4 && blueCount != 4) {
                    blueCount = 0
                    redCount += 1
                    if (redCount == 4) {
                        winner = GameConstants.RED
                        break
                    }
                } else if (board[row][col + shift] == GameConstants.BLUE && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount += 1
                    if (blueCount == 4) {
                        winner = GameConstants.BLUE
                        break
                    }
                } else if (board[row][col + shift] == GameConstants.EMPTY && redCount != 4 && blueCount != 4) {
                    redCount = 0
                    blueCount = 0
                }
                shift += 1
            }
            if (winner != 0) {
                break
            } else {
                shift = 0
                cutOff += 1
                redCount = 0
                blueCount = 0
            }
        }

        return winner
    }

    // Checks to see if there are no possible moves left --> tie
    fun checkForTie(): Int {
        var gameStatus = GameConstants.TIE
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                if (board[row][col] == GameConstants.EMPTY) {
                    gameStatus = 0
                    break
                }
            }
            if (gameStatus == 0) {
                break
            }
        }
        return gameStatus
    }

    /**
     * Print the game board
     */
    fun printBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("----------------------") // print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }
}