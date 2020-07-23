package quotes.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *
This project has been created by Abdallah El-Sakka in 13/7/20

Contact me on :

Whatsapp : +201010406009
Facebook : https://www.facebook.com/abdallassam
Github : https://github.com/AbdallahEssam501

All Rights Reserved.

 **/

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadQuotesDataFromFireBase()

    }

    private fun loadQuotesDataFromFireBase() {

        val ref = FirebaseDatabase.getInstance().getReference("quotes/")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.value
//                    Log.d("hello" , "data : $value")
                    QuotesList.add(value.toString())
                }

                //load quote
                randomizeQuotes()


                // I used try & catch here to avoid loading errors from user.

                try {
                    copyBtn.setOnClickListener {
                        val textToCopy = quote_textView.text.toString()
                        copyText(textToCopy)
                    }
                }catch (ex : Exception){}

                try {
                    nextBtn.setOnClickListener { randomizeQuotes() }
                }catch (ex : Exception){}

                try {
                    fav_btn.setOnClickListener { addToFavEvent() }
                }catch (ex : Exception){}

                try {
                    loadingAnimation()
                }catch (ex : Exception){}

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Toast.makeText(activity,"Please check your internet connection",Toast.LENGTH_SHORT).show()
//                Log.w("hello", "loadPost:onCancelled", databaseError.toException())

            }
        })
    }

    private fun copyText(textToCopy : String){

        val clipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText("text",textToCopy)

        clipboard.setPrimaryClip(clip)

        Toast.makeText(activity,"Quote Copied",Toast.LENGTH_SHORT).show()

    }

    private fun randomizeQuotes(){

        try {
            fav_btn.setImageResource(R.drawable.heart_rounded)
        }catch (ex : Exception){}

        addedToFav = false

        for (i in 0..QuotesList.size){
            QuotesList.shuffle()

            // used try here to avoid user errors !!
            try {
                quote_textView.text = QuotesList[i]
            }catch (ex : Exception){}

            break
        }
    }

    private fun addToFavEvent(){

        fav_btn.setImageResource(R.drawable.heart)

        if (!addedToFav){
            val quote = quote_textView.text.toString()

            val values = ContentValues()
            values.put("Title",quote)

            val dbManager = DbManager(this.requireActivity())
            val id = dbManager.insertQuotes(values)

            if (id > 0){

                Toast.makeText(activity,"Added to favourites",Toast.LENGTH_SHORT).show()
//                Log.d("db","Record Added Successfully")
            }else{

//                Log.d("db","Failed To Add Record")
            }
        }
        addedToFav = true
    }

    private fun loadingAnimation(){
        if (quote_textView.text == null){
            loadingAnim.visibility = View.VISIBLE
        }else{
            loadingAnim.visibility = View.GONE
        }
    }

    companion object {

        val QuotesList = ArrayList<String>()

        var addedToFav = false

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
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