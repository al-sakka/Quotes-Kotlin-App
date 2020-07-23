package quotes.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alphasForPhotos()

        funnyQuotes_Card.setOnClickListener { bundle(funnyCard_textView.text.toString()) }

        techQuotes_Card.setOnClickListener { bundle(techCard_textView.text.toString()) }

        eduQuotes_Card.setOnClickListener { bundle(eduCard_textView.text.toString()) }

        careerQuotes_Card.setOnClickListener { bundle(careerCard_textView.text.toString()) }

        warQuotes_Card.setOnClickListener { bundle(warCard_textView.text.toString()) }

        sportsQuotes_Card.setOnClickListener { bundle(sportsCard_textView.text.toString()) }

        timeQuotes_Card.setOnClickListener { bundle(timeCard_textView.text.toString()) }

        wishesQuotes_Card.setOnClickListener { bundle(wishesCard_textView.text.toString()) }

    }

    /**
     *
    This project has been created by Abdallah El-Sakka in 13/7/20

    Contact me on :

    Whatsapp : +201010406009
    Facebook : https://www.facebook.com/abdallassam
    Github : https://github.com/AbdallahEssam501

    All Rights Reserved.

     **/

    fun alphasForPhotos(){
        funnyPhoto.alpha = 0.05F
        techPhoto.alpha = 0.05F
        eduPhoto.alpha = 0.05F
        warPhoto.alpha = 0.05F
        timePhoto.alpha = 0.05F
        sportsPhoto.alpha = 0.05F
        wishesPhoto.alpha = 0.05F
        careerPhoto.alpha = 0.05F
    }

    fun bundle(title : String){
        val bundle = Bundle()
        bundle.putString("title",title)

        view?.findNavController()?.navigate(R.id.cardsFragment,bundle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}