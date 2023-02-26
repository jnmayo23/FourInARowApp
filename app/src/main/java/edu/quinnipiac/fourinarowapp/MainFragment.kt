/**
 * Main Fragment -- MainFragment Class
 * @author Jordan Mayo
 * @date 2/25/2023
 */

package edu.quinnipiac.fourinarowapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val startButton = view.findViewById<Button>(R.id.start)

        // Navigates to GameFragment when button is clicked, with username arg
        startButton.setOnClickListener {
            val usernameInput: EditText = view!!.findViewById(R.id.username_input)
            val username = usernameInput.text.toString()
            val bundle = bundleOf("username" to username)
            view.findNavController().navigate(R.id.action_mainFragment_to_gameFragment, bundle)
        }
        return view
    }

}