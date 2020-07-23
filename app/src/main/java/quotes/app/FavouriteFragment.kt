package quotes.app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fav_quotes_row.view.*
import kotlinx.android.synthetic.main.fragment_favourite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FavouriteFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        querySearch("%")

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

    companion object {



        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun querySearch(quoteTitle : String){

        val arrayList = ArrayList<data>()

        var dbManager = DbManager(this!!.requireActivity())
        val projections = arrayOf("ID","Title","Description")
        val selectionArgs = arrayOf(quoteTitle)

        val cursor = dbManager.query(projections,"Title like ?",selectionArgs,"Title")

        if (cursor.moveToFirst()){
            arrayList.clear()
            do {

                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))

                arrayList.add(data(id,title))

            }while (cursor.moveToNext())
        }

        val adapter = adapter(arrayList,this.requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(this.requireActivity())
        recyclerView.adapter = adapter


        //checking if the list is empty
        if (arrayList.isNullOrEmpty()){
            favouritesIfEmptyAnim.visibility = View.VISIBLE
            favouritesIfEmptyText.visibility = View.VISIBLE
        }else{
            favouritesIfEmptyAnim.visibility = View.INVISIBLE
            favouritesIfEmptyText.visibility = View.INVISIBLE
        }


    }

    inner class adapter(var arrayList: ArrayList<data>, var context : Context) :
        RecyclerView.Adapter<adapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(model: data) {

                itemView.quote_textView_card.text = model.nodeTitle

                itemView.removeFromFavBtn.setOnClickListener {

                    //delete quote
                    delete(it.context as FragmentActivity, model.nodeID.toString())

                    //refresh recyclerView
                    querySearch("%")

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layout =
                LayoutInflater.from(parent.context).inflate(R.layout.fav_quotes_row, parent, false)
            return ViewHolder(layout)
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(arrayList[position])

        }
    }

    fun delete(context : Context, value : String){
        val dbManager = DbManager(context as FragmentActivity)
        val selectionArgs = arrayOf(value)
        dbManager.delete("ID=?", selectionArgs)

    }
}
