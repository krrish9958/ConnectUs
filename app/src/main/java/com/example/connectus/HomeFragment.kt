package com.example.connectus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var dbReference: DatabaseReference
    private lateinit var reCyclerView : RecyclerView
    private lateinit var adapter: MyAdapter
   private lateinit var postList : ArrayList<PostData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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

//        dbReference = FirebaseDatabase.getInstance().getReference("posts")
//        postList = arrayListOf<PostData>()
//        adapter = MyAdapter(postList)
//
//        reCyclerView= requireView().findViewById(R.id.recylerView)
//        reCyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
//        reCyclerView.setHasFixedSize(true)
//        getPostData()
        view.findViewById<FloatingActionButton>(R.id.addPostBtn).setOnClickListener {
            var intent = Intent(requireContext(),AddPostActivity::class.java)
            startActivity(intent)


        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    private fun getPostData(){

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        var postData = postSnap.getValue(PostData::class.java)
                        postList.add(postData!!)
                    }
                    reCyclerView.adapter=adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
Toast.makeText(requireContext(),"${error.message}",Toast.LENGTH_SHORT).show()
            }

        })
    }
}