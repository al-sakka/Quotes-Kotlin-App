package quotes.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.cards_row.view.quote_textView_card
import kotlinx.android.synthetic.main.fragment_cards.*
import kotlinx.android.synthetic.main.fragment_favourite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 *
This project has been created by Abdallah El-Sakka in 13/7/20

Contact me on :

Whatsapp : +201010406009
Facebook : https://www.facebook.com/abdallassam
Github : https://github.com/AbdallahEssam501

All Rights Reserved.

 **/

/**
 * A simple [Fragment] subclass.
 * Use the [CardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTitle()

        loadingAnim()

        loadQuotes()

        backBtn.setOnClickListener {
            back()
        }

    }

    fun back(){
        activity?.onBackPressed()
    }

    fun loadingAnim(){

        if (list.isNullOrEmpty()){
            recyclerLoadingAnimation.visibility = View.VISIBLE
        }else{
            recyclerLoadingAnimation.visibility = View.INVISIBLE
        }
    }

    fun getTitle(){


        val title = arguments?.getString("title")
        cardTitle.text = title

        cardID = when(title){
            "Funny Quotes" -> "comedianQuotes/"
            "Tech Quotes" -> "techQuotes/"
            "Education Quotes" -> "educationQuotes/"
            "Career Quotes" -> "careerQuotes/"
            "War Quotes" -> "warQuotes/"
            "Sports Quotes" -> "sportsQuotes/"
            "Time Quotes" -> "timeQuotes/"
            "Wishes Quotes" -> "wishesQuotes/"

            else -> "quotes/"
        }
    }

    fun loadRecycler(){
        val adapter = adapter(list, this.requireActivity())
        card_recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        card_recyclerView.adapter = adapter
    }

    fun loadQuotes(){


        val ref = FirebaseDatabase.getInstance().getReference(cardID.toString())

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                for (postSnapshot in snapshot.children){
                    val value = postSnapshot.value
                    list.add(cardData(value.toString()))
                }

                list.shuffle()

                loadRecycler()
                loadingAnim()
            }
        })

    }


    companion object {

        var cardID : String? = null

        val list = ArrayList<cardData>()

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


data class cardData(val title : String)

class adapter(val arrayList: ArrayList<cardData>,val context : Context) :
    RecyclerView.Adapter<adapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(model : cardData){

            itemView.quote_textView_card.text = model.title

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cards_row,parent,false)
        return ViewHolder(layout)
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

    }
}